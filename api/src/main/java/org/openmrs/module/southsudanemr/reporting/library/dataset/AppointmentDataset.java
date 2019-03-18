package org.openmrs.module.southsudanemr.reporting.library.dataset;

import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.southsudanemr.reporting.library.cohorts.AppointmentCohortQueries;
import org.openmrs.module.southsudanemr.reporting.library.dimension.SsEmrCommonDimension;
import org.openmrs.module.southsudanemr.reporting.library.indicators.SsEmrGeneralIndicator;
import org.openmrs.module.southsudanemr.utils.SsEmrReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AppointmentDataset extends BaseDataSet {
	
	@Autowired
	private AppointmentCohortQueries appointmentCohortQueries;
	
	@Autowired
	private SsEmrCommonDimension ssEmrCommonDimension;
	
	@Autowired
	private SsEmrGeneralIndicator ssEmrGeneralIndicator;
	
	public DataSetDefinition constructAppointmentDataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Daily Appointments");
		dsd.addDimension("gender", SsEmrReportUtils.map(ssEmrCommonDimension.gender(), ""));
		//Start building the data set here
		addRow(
		    dsd,
		    "I1",
		    "Number of clients with appointments today",
		    SsEmrReportUtils.map(
		        ssEmrGeneralIndicator.getIndicator("Clients with appointments today",
		            SsEmrReportUtils.map(appointmentCohortQueries.getAppointmentsToday(), "")), ""), getGenderList());
		addRow(
		    dsd,
		    "I2",
		    "Number of patients who visited facility today",
		    SsEmrReportUtils.map(
		        ssEmrGeneralIndicator.getIndicator("Patients who visited facility today",
		            SsEmrReportUtils.map(appointmentCohortQueries.getClientsWhoVistedFacilityToday(), "")), ""),
		    getGenderList());
		
		addRow(
		    dsd,
		    "I3",
		    "Number of patients who missed appointment",
		    SsEmrReportUtils.map(
		        ssEmrGeneralIndicator.getIndicator("Patients who missed",
		            SsEmrReportUtils.map(appointmentCohortQueries.getClientsWhoMissedAppointment(), "")), ""),
		    getGenderList());
		
		addRow(dsd, "I4", "Number of patients who visited facility today without appointments", SsEmrReportUtils.map(
		    ssEmrGeneralIndicator.getIndicator("Patients who visited facility today without appointments", SsEmrReportUtils
		            .map(appointmentCohortQueries.getNumberOfClientsWhoVisitedFacilityTodayWithoutAppointment(), "")), ""),
		    getGenderList());
		return dsd;
	}
	
	private List<ColumnParameters> getGenderList() {
		ColumnParameters male = new ColumnParameters("Male", "Male", "gender=M", "01");
		ColumnParameters female = new ColumnParameters("Female", "Female", "gender=F", "02");
		ColumnParameters totals = new ColumnParameters("Totals", "Totals", "", "03");
		return Arrays.asList(male, female, totals);
	}
}
