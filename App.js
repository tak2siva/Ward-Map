      import React, { Component } from 'react';
      import PropTypes from 'prop-types';
      import Realm from 'realm';
      import DataMigration from './database/DataMigration';
      import {Schemas} from './database/schemas';
      import styles from './styles/appStyles';
      import WardInfoTile from './components/wardInfoTile';
      import Notice from './components/notice';
      import WaitSpinner from './components/waitSpinner';
      import SplashScreen from "rn-splash-screen";
      import {
        Platform,
        Dimensions,
        StyleSheet,
        Text,
        View,
        ScrollView,
        Button,
        requireNativeComponent,
        DeviceEventEmitter
      } from 'react-native';

      const GoogleMapView = requireNativeComponent('GoogleMapView', {
        name: 'GoogleMapView', 
        propTypes: {
          userLocation: PropTypes.object,
          ...View.propTypes
        }
      });


      class GeoPoint {
        constructor(latitude, longitude) {
          this.latitude = latitude;
          this.longitude = longitude;
        }
      }

      // const chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);

      const Events = {
        ClickMarker: 'ClickMarker',
        mapLoaded: 'mapLoaded'
      }

      export default class App extends Component<{}> {
        constructor(props) {
          super(props);
          this.state = {
            userLocation: null,
            noResult: null,
            mapLoaded: false
          }

          let that = this;

          DeviceEventEmitter.addListener('mapLoaded',  function(e: Event) {
            if (!e) {
              console.log("Unable to find ward");
              return;
            }
            console.log("===========maploaded event trigerd===========");
            that.setState({mapLoaded: true})


          });

          DeviceEventEmitter.addListener('ClickMarker',  function(e: Event) {
            if (!e) {
              console.log("Unable to find ward Info for click location");
              that.setState({noResult: true});
              return;
            }
            let wardNo = e.split(" ")[1];
            let wards = null;
            Realm.open({schema: [Schemas.WardInfoSchema]})
            .then(realm => {
              wards = realm.objects('WardInfo').filtered('wardNo = '+ wardNo);
              that.setState({
                marker_info: e,
                wardInfo: wards,
                noResult: false
              });
            });      
          });

        }

        componentDidMount() {
      // this.watchId = navigator.geolocation.watchPosition(
      navigator.geolocation.getCurrentPosition(
        (position) => {
          console.log("cordinates=====",position.coords.latitude,position.coords.longitude);
          this.setState({
            userLocation: new GeoPoint(position.coords.latitude, position.coords.longitude)})
        },
        (error) => {
          console.log("Error updating current location: ");
          console.log(error);},
          { enableHighAccuracy: true, timeout: 0},
          );
    }

    componentWillMount() {
      DataMigration.prototype.importCSVData();
      // navigator.geolocation.clearWatch(this.watchId);
      global.setTimeout(() => {
        SplashScreen.hide();
      }, 1000);
    }


    render() {
      if(!this.state.mapLoaded || !this.state.userLocation || this.state.noResult == null) {
        return ( <View  style={styles.container}>
          <GoogleMapView userLocation={ this.state.userLocation } />
          <WaitSpinner/>
          </View>)
        }
        return (<View style={styles.container}>
        <GoogleMapView userLocation={this.state.userLocation} style={styles.mapView} />
        {(this.state.noResult)
          ? <Notice
          textStyle = {styles.noInfoWaitText}
          viewStyle = {styles.noInfoWaitView}
          content = {{"info":"Sorry","status":"No Information Found for your Location"}}
          />
          : < WardInfoTile content={this.state.wardInfo} /> }
          </View>)
        }
      }
