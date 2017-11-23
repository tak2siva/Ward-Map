import React from 'react';
import PropTypes from 'prop-types';
import {Text,View} from 'react-native';

class WardInfoTile extends React.Component {
	render = () =>{
		const {content,textStyle,viewStyle} = this.props;
		return (
			<View style={viewStyle}>
				<Text style={textStyle}>Ward No : {content[0].wardNo} </Text>
	        	<Text style={textStyle}>Ward Name : {content[0].zoneName} </Text>
	       		<Text style={textStyle}>Zone No : {content[0].zoneNo}</Text>
	       		<Text style={textStyle}>Zonal office adress : {content[0].zonalOfficeAddress}</Text>
	       		<Text style={textStyle}>Zonal officer email : {content[0].zonalOfficerEmail}</Text>
	       		<Text style={textStyle}>Zonal officer land line : {content[0].zonalOfficerLandLine}</Text>
	       		<Text style={textStyle}>Zonal officer mobile: {content[0].zonalOfficerMobile}</Text>
	       	</View>
			);
	}
} 

WardInfoTile.propTypes = {
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
};

export default WardInfoTile;