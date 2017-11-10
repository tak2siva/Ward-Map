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
        centerCoordinate: PropTypes.object,
        randomKey: PropTypes.number, // Hack to call java method with view instance
        ...View.propTypes
      }
});

export default class App extends Component<{}> {
  constructor(props) {
    super(props);
    this.state = { 
      latitude: 13.082680,
      longitude: 80.270718
    }
  }

  onClickLocate() {
    this.setState({latitude: 11.016844, longitude: 76.955832, randomKey: Math.random()});
  }

  render() {
    return(
      <View style={styles.container}>
        <Text> Hello </Text>
        <MapView 
          latitude={this.state.latitude}
          longitude={this.state.longitude}
          randomKey={Math.random()}
          style={styles.mapView} 
        />
        <Button 
          onPress={this.onClickLocate.bind(this)}
          title='Locate Me'/>
      </View>
    );
  }
}
