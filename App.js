import React, { Component } from 'react';
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
  Button,
  Modal,
  requireNativeComponent,
  TouchableHighlight,
  DeviceEventEmitter
} from 'react-native';

const GoogleMapView = requireNativeComponent('GoogleMapView', {
      name: 'GoogleMapView', 
      propTypes: {
        latitude: PropTypes.number,
        longitude: PropTypes.number,
        enableMarker: PropTypes.bool,
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

class GeoPoint {
  constructor(latitude, longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

const chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);
const Events = {
  MAP_LONG_PRESS_EVENT: 'MAP_LONG_PRESS_EVENT',
  ClickMarker: 'ClickMarker'
}
 
export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = {
      userLocation: chennaiGeoPoint
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
      let wardNo = e.split(" ")[1];
      let wards = null;
      Realm.open({schema: [Schemas.WardInfoSchema]})
        .then(realm => {
          wards = realm.objects('WardInfo').filtered('wardNo = '+ wardNo);
            that.setState({
              marker_info: e,
              wardInfo: wards
            });
      });      
    });
  }

  componentWillMount() {
    DataMigration.prototype.importCSVData();
  }

  setModalVisible(visible) {
    this.setState({modalVisible: visible});
  }

  updateCurrentLocation() {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log("========= GPS reading ================");
        console.log(new GeoPoint(position.coords.latitude, position.coords.longitude));
        this.setState({
          userLocation: new GeoPoint(position.coords.latitude, position.coords.longitude),
        });
      },
      (error) => {
        console.log("Error updating current location: ");
        console.log(error);
      },
      {maximumAge: 0, timeout: 20000}
    );
  }

  onClickLocate() {
    this.updateCurrentLocation();
    this.setState({randomKey: Math.random(),enableMarker: true});
  }

  render() {

    var wardNo  = (this.state.wardInfo === undefined) ? 'No Ward info Available' : this.state.wardInfo[0].wardNo;
    var zoneName  = (this.state.wardInfo === undefined) ? 'No Zone info Available' : this.state.wardInfo[0].zoneName
    var zoneNo = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zoneNo
    var zonalOfficeAddress = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficeAddress
    var zonalOfficerEmail = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerEmail
    var zonalOfficerLandLine = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerLandLine
    var zonalOfficerMobile = (this.state.wardInfo === undefined) ? 'No ZoneNo info Available' : this.state.wardInfo[0].zonalOfficerMobile

    return(
      <View style={styles.container}>

        <Modal
          animationType="slide"
          transparent={false}
          style={{marginTop:30}}
          visible={this.state.modalVisible}
          onRequestClose={() => {alert("Modal has been closed.")}}
          >

          <View style={styles.closeModal}>
            <TouchableHighlight onPress={() => {
              this.setModalVisible(!this.state.modalVisible)
            }}>
            <Text style={styles.close}>✕</Text>
            </TouchableHighlight>
          </View>

         <View style={{marginTop: 50}}>
          <View>
            <Text style={styles.wardInfoText}>wardNo : {wardNo}</Text>
            <Text style={styles.wardInfoText}>zoneName : {zoneName}</Text>
            <Text style={styles.wardInfoText}>zoneNo : {zoneNo}</Text>
            <Text style={styles.wardInfoText}>zonalOfficeAddress : {zonalOfficeAddress}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerEmail : {zonalOfficerEmail}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerLandLine : {zonalOfficerLandLine}</Text>
            <Text style={styles.wardInfoText}>zonalOfficerMobile: {zonalOfficerMobile}</Text>

          </View>
         </View>
        </Modal>




        <GoogleMapView
          style={styles.mapView}
        />
        <View style={styles.small_ward_info_tile}>
          <Text style={styles.wardInfoText}> Ward No : {wardNo} </Text>
          <Text style={styles.wardInfoText}> Ward Name : {zoneName} </Text>
        </View>

        <View style={styles.more_info_holder}>
          
          <TouchableHighlight onPress={() => {
          this.setModalVisible(true)
          }}>
            <Text style={{fontSize: 15}}>
              More Info
            </Text>
          </TouchableHighlight>

          <View style={styles.locateMe}>
            <Button 
            onPress={this.onClickLocate.bind(this)}
            title='Locate Me'/>
          </View>
        </View>
      </View>
    );
  }
}
