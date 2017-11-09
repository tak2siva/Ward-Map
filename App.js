import React, { Component } from 'react';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  View,
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
  }
});

export default class App extends Component<{}> {
  render() {
    const url = 'https://c.tile.openstreetmap.org/{z}/{x}/{y}.png';
    const CustomImageView = requireNativeComponent('CustomImageView', {name: 'CustomImageView', propTypes: {...View.propTypes}});

    return(
      <View style={styles.container}>
        <Text> Hello </Text>
        <CustomImageView style={styles.imageBox}/>
      </View>
    );
  }
}
