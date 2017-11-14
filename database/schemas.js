export var Schemas = {
	CsvImportSchema : {
		name: 'CsvImport',
	    primaryKey: 'version',
	    properties: {
	        version: 'string',
	        imported: {type: 'bool', default: false}
	  	}
	},
	WardInfoSchema :{
	  name: 'WardInfo',
	  primaryKey: 'wardNo',
	  properties: {
	    wardNo: 'int',
	    zoneNo: 'string',
	    zoneName: 'string',
	    zonalOfficeAddress: 'string',
	    zonalOfficerEmail: 'string',
	    zonalOfficerLandLine: 'string',
	    zonalOfficerMobile: 'string'
	  }
	}

}