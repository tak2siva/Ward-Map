import React from 'react';
import {Text,View,Image} from 'react-native';

class WaitSpinner extends React.Component {
	render = () =>{
		return (
			<View style={{flex: 1,flexDirection: 'column',alignItems: 'center', justifyContent: 'center',backgroundColor: 'white'}}>
				<View>
					<Image
						style={{width: 120, height: 150,resizeMode: 'contain'}}
		          		source={require('../react_assets/images/pointer.png')}
		        	/>
		        </View >
		        <View>
	       			<Text style={{fontSize: 20}}>GETTING YOUR LOCATION</Text>
	       		</View>
	       		<View>
		       		<Image
		       			style={{width: 120, height: 50,resizeMode: 'contain'}}
		          		source={require('../react_assets/images/loader.gif')}
		        	/>
		        </View>
	       	</View>
			);
	}
} 


export default WaitSpinner;