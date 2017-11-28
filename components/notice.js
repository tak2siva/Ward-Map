import React from 'react';
import PropTypes from 'prop-types';
import {Text,View} from 'react-native';

class Wait extends React.Component{
	static propTypes = {
		content : PropTypes.object.isRequired,
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
				<Text style={[textStyle,{textAlign: "center"}]}>{content.info}</Text>
				<Text style={[textStyle,{textAlign: "center"}]}> {content.status} </Text>
	       	</View>
		);
	}
} 

export default Wait;