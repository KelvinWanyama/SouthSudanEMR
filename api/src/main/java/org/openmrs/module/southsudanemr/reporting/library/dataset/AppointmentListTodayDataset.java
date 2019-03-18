package org.openmrs.module.southsudanemr.reporting.library.dataset;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.southsudanemr.metadata.PatientIdentifierTypes;
import org.openmrs.module.southsudanemr.reporting.library.cohorts.AppointmentCohortQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentListTodayDataset extends BaseDataSet {
	
	@Autowired
	private AppointmentCohortQueries appointmentCohortQueries;
	
	public DataSetDefinition constructAppointmentListTodayDataset() {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.setName("List Today");
		dsd.addParameters(getParameters());
		dsd.addRowFilter(appointmentCohortQueries.getAppointmentsToday(), "");
		
		PatientIdentifierType aRTNo = MetadataUtils.existing(PatientIdentifierType.class,
		    PatientIdentifierTypes.ART_NUMBER.uuid());
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        aRTNo.getName(), aRTNo), identifierFormatter);
		
		dsd.addColumn("ART Number", identifierDef, "");
		dsd.addColumn("Name of Client", new PreferredNameDataDefinition(), (String) null);
		dsd.addColumn("Sex", new GenderDataDefinition(), (String) null);
		dsd.addColumn("Birth Date", new BirthdateDataDefinition(), (String) null);
		
		return dsd;
	}
	
}
