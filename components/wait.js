import React from 'react';
import PropTypes from 'prop-types';
import {Text,View} from 'react-native';

class Wait extends React.Component{
	static propTypes = {
		content : PropTypes.string.isRequired,
		textStyle : PropTypes.oneOfType([
      		PropTypes.array,
      		PropTypes.number,
      		PropTypes.shape({}),
    	]).isRequired,
    	viewStyle : PropTypes.oneOfType([
      		PropTypes.array,
      		PropTypes.number,
      		PropTypes.shape({}),
    	]).isRequired,
	}

	render = () =>{
		const {content,textStyle,viewStyle} = this.props;
		return (
			<View style={viewStyle}>
				<Text style={textStyle}>Tried to fetch information.</Text>
				<Text style={textStyle}> {content} </Text>
	       	</View>
		);
	}
} 

export default Wait;