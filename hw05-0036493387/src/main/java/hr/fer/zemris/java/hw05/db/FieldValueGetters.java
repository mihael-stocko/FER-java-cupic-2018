package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A collection of classes that implement {@link IFieldValueGetter}.
 * 
 * @author Mihael Stočko
 *
 */
public class FieldValueGetters {

	/**
	 * Overrides the get method so that it returns firstName.
	 */
	 public static final IFieldValueGetter FIRST_NAME = new IFieldValueGetter() {
		
		@Override
		public String get(StudentRecord record) {
			Objects.requireNonNull(record, "record cannot be null.");
			return record.getName();
		}
	};
	
	/**
	 * Overrides the get method so that it returns lastName.
	 */
	public static final IFieldValueGetter LAST_NAME = new IFieldValueGetter() {
		
		@Override
		public String get(StudentRecord record) {
			Objects.requireNonNull(record, "record cannot be null.");
			return record.getSurname();
		}
	};
	
	/**
	 * Overrides the get method so that it returns jmbag.
	 */
	public static final IFieldValueGetter JMBAG = new IFieldValueGetter() {
		
		@Override
		public String get(StudentRecord record) {
			Objects.requireNonNull(record, "record cannot be null.");
			return record.getJmbag();
		}
	};
}
