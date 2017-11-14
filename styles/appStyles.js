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
    paddingVertical: 5,
  },
  locateMe: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    width: '30%',
  },
  modal: {
    position: 'absolute',
    zIndex: 2,
    width: '100%',
    height: '100%',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
  },
  more_info_holder: {
    borderTopColor: '#e1e1e1',
    borderTopWidth: 1,
    position: 'absolute',
    bottom: 0,
    width: '100%',
    backgroundColor: '#f5f5f5',
    borderBottomWidth: 1 ,
    paddingVertical: 7,
    paddingHorizontal: 10,
  },
  closeModal: {
    backgroundColor: '#f5f5f5',
    width: 40,
    position: 'absolute',
    top: 0,
    right: 0,
    justifyContent: 'center',
    paddingVertical: 4,
    paddingHorizontal: 4,
    marginVertical: 1,
    marginHorizontal: 2
  },
  close: {
    color: 'gray',
    fontSize: 22
  },
  wardInfoText: {
    marginVertical: 3,
    marginHorizontal: 5,
    fontSize: 18
  }
});