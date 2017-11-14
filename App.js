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
  requireNativeComponent,
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

    return(
      <View style={styles.container}>
        <Text> Hello New </Text>
        <GoogleMapView
          style={styles.mapView}
        />
        <Button 
          onPress={this.onClickLocate.bind(this)}
          title='Locate Me'/>
        <View style={styles.small_ward_info_tile}>
          <Text> Ward No : {wardNo} </Text>
          <Text> Ward Name : {zoneName} </Text>
        </View>
      </View>
    );
  }
}
