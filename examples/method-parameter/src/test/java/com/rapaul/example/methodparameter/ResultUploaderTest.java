package com.rapaul.example.methodparameter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class ResultUploaderTest {

	private ResultRepository repository = mock(ResultRepository.class);
	private Clock clock = new FixedTimeClock(new Date());
	private ResultUploader uploader = new ResultUploader(repository, clock);

	@Test
	public void storesSingleResult() {
		ResultInputSource source = new TestingResultInputSource("patient-id-123", new String[] { "value1" });

		uploader.upload(source);

		verify(repository).store(Arrays.asList(new Result("patient-id-123", "value1")), clock.now());
	}

	@Test
	public void submissionsWithoutResultsAreIgnored() throws Exception {
		ResultInputSource source = new TestingResultInputSource("patient-id-123", null);

		uploader.upload(source);

		verifyZeroInteractions(repository);
	}

	class TestingResultInputSource implements ResultInputSource {

		private String patientId;
		private String[] values;

		public TestingResultInputSource(String patientId, String[] values) {
			this.patientId = patientId;
			this.values = values;
		}

		@Override
		public String getPatientId() {
			return patientId;
		}

		@Override
		public String[] getValues() {
			return values;
		}

	}
}