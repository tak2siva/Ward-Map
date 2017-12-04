import React from 'react';
import PropTypes from 'prop-types';
import {Text,View} from 'react-native';
import styles from '../styles/appStyles';

class Wait extends React.Component{
	static propTypes = {
		content : PropTypes.object.isRequired,
	}

	render = () =>{
		const {content,textStyle,viewStyle} = this.props;
		return (
			<View style={styles.noInfoWaitView}>
				<Text style={[styles.noInfoWaitText,{textAlign: "center"}]}>{content.info}</Text>
				<Text style={[styles.noInfoWaitText,{textAlign: "center"}]}> {content.status} </Text>
	       	</View>
		);
	}
} 

export default Wait;