package org.openmrs.module.southsudanemr.reporting.library.cohorts;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.southsudanemr.metadata.SsDictionary;
import org.openmrs.module.southsudanemr.queries.Queries;
import org.springframework.stereotype.Component;

@Component
public class AppointmentCohortQueries {
	
	public CohortDefinition getAppointmentsToday() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Patients who have appointments today");
		sql.setQuery(Queries.getAppointmentToday(SsDictionary.getConcept(SsDictionary.NEXT_VISIT_DATE).getConceptId()));
		return sql;
	}
	
	public CohortDefinition getClientsWhoVistedFacilityToday() {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Patients who visited the facility today");
		sql.setQuery(Queries.getClientsWhoVistedFacilityToday());
		return sql;
	}
}
