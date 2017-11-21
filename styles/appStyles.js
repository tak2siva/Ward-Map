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
    height: '80%',
    width: '100%',
  },
  block: {
    display: 'flex',
  },
  displayNone: {
    display: 'none',
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
  small_ward_info_tile :{
    width: '100%',
    paddingHorizontal: 10,
    backgroundColor: 'white',
    paddingVertical: 5,
  },
  locateMe: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    width: '50%',
    borderTopColor: '#e7e7e7',
    borderTopWidth: 1,
  },
  modal: {
    position: 'absolute',
    width: '100%',
    bottom: 70,
    paddingHorizontal: 7
  },
  more_info_holder: {
    borderTopColor: '#e7e7e7',
    borderTopWidth: 1,
    position: 'absolute',
    bottom: 0,
    width: '100%',
    backgroundColor: '#f5f5f5',
    borderBottomWidth: 1 ,
    paddingVertical: 7,
    paddingHorizontal: 10,
  },
  wardInfoText: {
    marginVertical: 7,
    marginHorizontal: 5,
    fontSize: 18
  }
});