import React, { Component } from 'react';
import PopupDialog, { SlideAnimation } from 'react-native-popup-dialog';
import PropTypes from 'prop-types';
import Realm from 'realm';
import DataMigration from './database/DataMigration'
import {Schemas} from './database/schemas'
import styles from './styles/appStyles';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  View,
  ScrollView,
  Button,
  requireNativeComponent,
  DeviceEventEmitter
} from 'react-native';

const GoogleMapView = requireNativeComponent('GoogleMapView', {
      name: 'GoogleMapView', 
      propTypes: {
        userLocation: PropTypes.object,
        randomKey: PropTypes.number, // Hack to call java method with view instance
        ...View.propTypes
      }
});

const options = {
  enableHighAccuracy: true,
  timeout: 5000,
  maximumAge: 0
};
const slideAnimation = new SlideAnimation({
  slideFrom: 'bottom',
});

class GeoPoint {
  constructor(latitude, longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

// const chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);

const Events = {
  MAP_LONG_PRESS_EVENT: 'MAP_LONG_PRESS_EVENT',
  ClickMarker: 'ClickMarker'
}
 
export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = {
      userLocation: null,
      isWardInfoClosed: true  
    }

    let that = this;

    DeviceEventEmitter.addListener('MAP_LONG_PRESS_EVENT',  function(e: Event) {
      console.log(e);
      that.setState({
        randomKey: Math.random(),
        enableMarker: true,
        userLocation: new GeoPoint(e.latitude, e.longitude)
      });
    });

    DeviceEventEmitter.addListener('ClickMarker',  function(e: Event) {
      if (!e) {
        console.log("Unable to find ward Info for click location");
        that.setState({noResult: true});
        return;
      }

      let wardNo = e.split(" ")[1];
      let wards = null;
      Realm.open({schema: [Schemas.WardInfoSchema]})
        .then(realm => {
          wards = realm.objects('WardInfo').filtered('wardNo = '+ wardNo);
            that.setState({
              marker_info: e,
              wardInfo: wards,
              noResult: false
            });
      });      
    });
  }

  componentDidMount() {
    this.watchId = navigator.geolocation.watchPosition(
      (position) => {
        this.setState({
          userLocation: new GeoPoint(position.coords.latitude, position.coords.longitude),
        },()=>{
          this.setState({randomKey: Math.random()

        })});
      },
      (error) => this.setState({ error: error.message }),
      { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000, distanceFilter: 10 },
    );
  }

  componentWillMount() {
    DataMigration.prototype.importCSVData();
    navigator.geolocation.clearWatch(this.watchId);
  }


  updateCurrentLocation() {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log("========= GPS reading ================");
        console.log(new GeoPoint(position.coords.latitude, position.coords.longitude));
        this.setState({
          userLocation: new GeoPoint(position.coords.latitude, position.coords.longitude),
        },()=>{
          this.setState({randomKey: Math.random()

        })});
      },
      (error) => {
        console.log("Error updating current location: ");
        console.log(error);
      },
      {maximumAge: 0, enableHighAccuracy: true, timeout: 20000}
    );
  }

  onClickLocate() {
    this.updateCurrentLocation();
    this.popupDialog.dismiss();
    this.setState({isWardInfoClosed: true})
  }
  toggleShow(){
    this.setState({isWardInfoClosed: !this.state.isWardInfoClosed}, () => {
      if(this.state.isWardInfoClosed){
        this.popupDialog.dismiss();
      }
    });
  }
  disablePopUp(){
    this.setState({isWardInfoClosed: true})
  }
      
  render() {
    const slideAnimation = new SlideAnimation({
      slideFrom: 'bottom',
    });

    var wardNo  = (this.state.wardInfo === undefined) ? 'No Ward info Available' : this.state.wardInfo[0].wardNo;
    var zoneName  = (this.state.wardInfo === undefined) ? 'No Zone info Available' : this.state.wardInfo[0].zoneName
    var zoneNo = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zoneNo
    var zonalOfficeAddress = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficeAddress
    var zonalOfficerEmail = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerEmail
    var zonalOfficerLandLine = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerLandLine
    var zonalOfficerMobile = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerMobile

    return(
      <View style={styles.container}>
        <GoogleMapView
            userLocation={this.state.userLocation}
            randomKey={this.state.randomKey}
            style={styles.mapView}
          />

        <PopupDialog
          ref={(popupDialog) => { this.popupDialog = popupDialog; }}
          dialogStyle={styles.modal}
          onDismissed={() =>{
            this.disablePopUp();
          }}
        
        >
        <ScrollView> 
          <View>
            <Text>{this.state.noResult ? 'No Result found for this location' : ''}</Text>  
            <Text style={styles.wardInfoText}>wardNo : {wardNo}</Text>
            <Text style={styles.wardInfoText}>zoneName : {zoneName}</Text>
            <Text style={styles.wardInfoText}>zoneNo : {zoneNo}</Text>
            <Text style={styles.wardInfoText}>zonalOfficeAddress : {zonalOfficeAddress}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerEmail : {zonalOfficerEmail}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerLandLine : {zonalOfficerLandLine}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerMobile: {zonalOfficerMobile}</Text>
          </View>
        </ScrollView>
        </PopupDialog>
        
        <View style={[styles.small_ward_info_tile, (this.state.isWardInfoClosed ? styles.block : styles.displayNone )]}
          id='view1' >
          <Text style={styles.wardInfoText}> Ward No : {wardNo} </Text>
          <Text style={styles.wardInfoText}> Ward Name : {zoneName} </Text>
        </View>
     
        <View style={styles.more_info_holder}>
          <Text style={styles.moreInfoText}
            onPress={() => {
                this.popupDialog.show();
                this.toggleShow();
            }}>
            More Info
          </Text>
          <View style={styles.locateMe}>
              <Button style={styles.locateMeBtn}
                onPress={this.onClickLocate.bind(this)}
                title='Locate Me'/>
          </View>
        </View>
        
      </View>
    );
  }
}
