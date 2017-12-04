import { StyleSheet } from 'react-native';

export default StyleSheet.create({
	headerTextView: {
		paddingVertical: 10,
		top: 15
	},
	headerTextStyle: {
	  	color: 'black',
	    fontSize: 22,
	    fontWeight: 'bold',
	    bottom: 10
  	},
  	ward_info_tile :{
	    width: '100%',
	    paddingHorizontal: 30,
	    backgroundColor: 'white',
	},
    wardInfoText: {
      	paddingVertical: 7,
      	paddingHorizontal: 5,
      	fontSize: 20,
      	color: 'black',
      	fontFamily: 'sans-serif-thin'
    },
    flexRow: {
		flex: 1, 
		flexDirection: 'row'
    },
   	smallBox: {
   		flex: 1,
   		backgroundColor: "#af7cdb",
   		alignItems: 'center',
   		justifyContent: 'center',
   		height: 60,
   		width: 40,
   		top: 12
   	},
    boxText:{
    	fontSize: 25,
    	fontWeight: 'bold',
    	fontFamily: 'notoserif',
    	color: 'white'
    },
    bigBox: {

    }
});