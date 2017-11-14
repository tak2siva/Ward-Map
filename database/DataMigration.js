import Realm from 'realm';
import {Schemas} from './schemas'

export default class DataMigration{
	importCSVData() {
	    Realm.open({schema: [Schemas.CsvImportSchema, Schemas.WardInfoSchema]})
	      .then(realm => {
	        let meta = realm.objects('CsvImport').filtered('version = "v1"');
	        if (meta && meta['0'] && meta['0'].imported) {
	          this.realm = realm;
	          console.log("================= Skipping Import ====================");
	          return;
	        }

	        const WardMapV1 = require('../react_assets/chennai_ward_map_v1.json');
	        console.log("============= Importing CSV ====================================")
	        for (let row of WardMapV1) {
	          try {
	            
	            console.log(row);
	            realm.write(() => {
	              realm.create('WardInfo', {
	                wardNo: Number.parseInt(row[0]), 
	                zoneNo: row[1], 
	                zoneName: row[2], 
	                zonalOfficeAddress: row[3], 
	                zonalOfficerEmail: row[4],
	                zonalOfficerLandLine: row[5],
	                zonalOfficerMobile: row[6]
	              });
	            });
	          } catch(e) {
	            console.log('error on importing csv');
	            console.log(e);
	          }
	        }
	        console.log("========= Import Success ============");
	        try {
	          realm.write(() => {
	            realm.create('CsvImport', {version: 'v1', imported: true});  
	          });
	        } catch(e) {
	          console.log('error on saving import log');
	          console.log(e);
	        }
	        
	      });
	}

}

