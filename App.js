import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Realm from 'realm';
import DataMigration from './database/DataMigration';
import {Schemas} from './database/schemas';
import styles from './styles/appStyles';
import WardInfoTile from './components/wardInfoTile';
import Wait from './components/wait';
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


class GeoPoint {
  constructor(latitude, longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

// const chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);

const Events = {
  ClickMarker: 'ClickMarker',
}
 
export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = {
      userLocation: null,
      isWardInfoClosed: true  
    }

    let that = this;

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
      (error) => {
        console.log("Error updating current location: ");
        console.log(error);},
      { enableHighAccuracy: true, timeout: 0, maximumAge: 1000, distanceFilter: 10 },
    );
  }

  componentWillMount() {
    DataMigration.prototype.importCSVData();
    navigator.geolocation.clearWatch(this.watchId);
  }

      
  render() {
    let wardInfo = null;
    if (this.state.wardInfo === undefined || this.state.noResult){
      wardInfo = <Wait textStyle={styles.noInfoWaitText} viewStyle={styles.noInfoWaitView} content = {"No Information Found"} />;
    }else{
      wardInfo = <WardInfoTile content={this.state.wardInfo}
              textStyle={styles.wardInfoText}
              viewStyle={styles.ward_info_tile} />;
    }

    return(
      <View style={styles.container}>
        <GoogleMapView
            userLocation={this.state.userLocation}
            randomKey={this.state.randomKey}
            style={styles.mapView}
          />
        <View>
          <ScrollView>
            {wardInfo}
          </ScrollView>
        </View>
      </View>
    );
  }
}
