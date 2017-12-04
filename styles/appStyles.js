import { StyleSheet } from 'react-native';

export default StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: 'white'
  },
  imageBox: {
    height: 200,
    width: '100%',
  },
  mapView: {
    height: '20%',
    width: '100%',
  },
  buttonContainer: {
    flexDirection: 'row',
    marginVertical: 20,
    backgroundColor: 'transparent',
  },
  noInfoWaitText: {
    paddingVertical: 15,
    paddingHorizontal: 5,
    color: '#A9A9A9',
    fontSize: 20,
    fontWeight: 'bold'
  },
  noInfoWaitView: {
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  headerText: {
    color: '#A9A9A9',
    fontSize: 20,
    fontWeight: 'bold'
  }

});