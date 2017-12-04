import React from 'react';
import PropTypes from 'prop-types';
import {Text,View,ScrollView} from 'react-native';
import styles from '../styles/wardInfoTile';
import RomanToDec from '../utils/romanToDec';

class WardInfoTile extends React.Component {
	render = () =>{
		const {content} = this.props;
		let zoneNumber = RomanToDec.prototype.toNumber(content[0].zoneNo);
		return (
			<ScrollView style={[styles.ward_info_tile,{flex: 1}]}>
				<View style={{width: '100%'}}>
					<View style={styles.headerTextView}>
						<Text style={styles.headerTextStyle}> ZONE : {content[0].zoneName} </Text>
					</View>

					<View style={styles.flexRow}>
						<View style={[{flex: 4},styles.bigBox]}>
				       		<Text style={[styles.wardInfoText]}>ZONAL OFFICE ADDRESS : {content[0].zonalOfficeAddress.toUpperCase()}</Text>
				       	</View>
				       	<View style={styles.smallBox}>
				       		<Text style={styles.boxText}>{zoneNumber}</Text>
						</View>
					</View>
		       		<View>
			       		<Text style={styles.wardInfoText}>OFFICE EMAIL : {content[0].zonalOfficerEmail}</Text>
			       		<Text style={styles.wardInfoText}>OFFICE LANDLINE : {content[0].zonalOfficerLandLine}</Text>
			       		<Text style={styles.wardInfoText}>OFFICER MOBILE : {content[0].zonalOfficerMobile}</Text>
			       	</View>

			       	{!content[0].wardNumber ? <View style={styles.headerTextView}>
						<Text style={styles.headerTextStyle}> WARD : No info available </Text>
					</View> :
					    <View style={{width: '100%',top: 20,flex:1}}>
					    	<View style={styles.flexRow}>
								<View style={[{flex: 4},styles.bigBox]}>
						       		<Text style={[styles.wardInfoText]}>WARD OFFICE ADDRESS : {(content[0].wardOfficeAddress || 'NA').toUpperCase()}</Text>
						       	</View>
						       	<View style={styles.smallBox}>
						       		<Text style={styles.boxText}>{wardNumber}</Text>
								</View>
							</View>
				       		<View>
					       		<Text style={[styles.wardInfoText,{fontWeight: 'bold'}]}> CONTACT : {content[0].wardOfficerMobile || "NA"}</Text>
					       	</View>
					    </View>
			   		 }


			    </View>
			       	
		    </ScrollView>
			);
	}
} 

WardInfoTile.propTypes = {
		content : PropTypes.object.isRequired,
};

export default WardInfoTile;