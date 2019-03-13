package org.openmrs.module.southsudanemr.queries;

import org.openmrs.module.southsudanemr.utils.SsEmrReportUtils;

public class Queries {
	
	public static final String getAppointmentToday(int conceptId) {
		return "SELECT pa.patient_id FROM patient pa INNER JOIN encounter e ON pa.patient_id=e.patient_id"
		        + " INNER JOIN obs o ON pa.patient_id=o.person_id WHERE o.voided=0 AND o.concept_id=" + conceptId
		        + " AND e.voided=0 AND o.value_datetime IS NOT NULL  AND o.value_datetime BETWEEN '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getStartOfDay()) + "' AND '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getEndOfDay()) + "'";
	}
	
	public static final String getClientsWhoVistedFacilityToday() {
		return "SELECT pa.patient_id FROM patient pa INNER JOIN encounter e ON pa.patient_id=e.patient_id"
		        + " INNER JOIN visit v ON pa.patient_id=v.patient_id WHERE v.voided=0 "
		        + " AND e.voided=0 AND v.date_started IS NOT NULL  AND v.date_started BETWEEN '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getStartOfDay()) + "' AND '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getEndOfDay()) + "'";
	}
}
