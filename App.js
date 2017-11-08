import React, { Component } from 'react';
import MapView, { MAP_TYPES, PROVIDER_DEFAULT } from 'react-native-maps';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  View
} from 'react-native';

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  map: {
    position: 'absolute',
    top: 20,
    left: 0,
    right: 0,
    bottom: 0,
  },
  bubble: {
    flex: 1,
    backgroundColor: 'rgba(255,255,255,0.7)',
    paddingHorizontal: 18,
    paddingVertical: 12,
    borderRadius: 20,
  },
  buttonContainer: {
    flexDirection: 'row',
    marginVertical: 20,
    backgroundColor: 'transparent',
  },
});

export default class App extends Component<{}> {
  render() {
    const url = 'https://c.tile.openstreetmap.org/{z}/{x}/{y}.png';

    return(
      <View style={styles.container}>
      <MapView
        provider={PROVIDER_DEFAULT}
        mapType={MAP_TYPES.STANDARD}
        style={ styles.map }
        initialRegion={{
          latitude: 37.78825,
          longitude: -122.4324,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        <MapView.UrlTile urlTemplate={url} zindex={-1}/>

      </MapView>
      <View style={styles.buttonContainer}>
          <View style={styles.bubble}>
            <Text>Custom Tiles</Text>
          </View>
        </View>
      </View>
    );
  }
}
