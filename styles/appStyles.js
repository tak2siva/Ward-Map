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
  },
  imageBox: {
    height: 200,
    width: '100%',
  },
  mapView: {
    height: '50%',
    width: '100%',
  },
  buttonContainer: {
    flexDirection: 'row',
    marginVertical: 20,
    backgroundColor: 'transparent',
  },
  ward_info_tile :{
    width: '100%',
    paddingHorizontal: 15,
    backgroundColor: 'white',
    paddingLeft: 10,
  },
  wardInfoText: {
    paddingVertical: 7,
    paddingHorizontal: 5,
    fontSize: 18

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
  }
});