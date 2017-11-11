import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  View,
  Button,
  requireNativeComponent
} from 'react-native';

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  imageBox: {
    height: 200,
    width: '100%',
  },
  mapView: {
    height: '80%',
    width: '100%',
  }
});

const MapView = requireNativeComponent('OSMapView', {
      name: 'OSMapView', 
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
 
export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = {
      userLocation: chennaiGeoPoint
    }
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
    return(
      <View style={styles.container}>
        <Text> Hello </Text>
        <MapView 
          randomKey={Math.random()}
          enableMarker={true}
          userLocation={this.state.userLocation}
          style={styles.mapView}
        />
        <Button 
          onPress={this.onClickLocate.bind(this)}
          title='Locate Me'/>
        <Text>
          {JSON.stringify(this.state)}
        </Text>  
      </View>
    );
  }
}
