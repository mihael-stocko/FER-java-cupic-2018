package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This program shows the usage of streams.
 * 
 * @author Mihael Stočko
 *
 */
public class StudentDemo {

	/**
	 * Main method. Arguments not used.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/students.txt"), 
				StandardCharsets.UTF_8);
		List<StudentRecord> records = null;
		try {
			records = convert(lines);
		} catch(Exception e) {
			System.out.println("The data in the provided file is not in the required format.");
			System.exit(1);
		}
		
		System.out.println(vratiBodovaViseOd25(records));
		
		System.out.println();
		
		System.out.println(vratiBrojOdlikasa(records));
		
		System.out.println();
		
		for(StudentRecord s : vratiListuOdlikasa(records)) {
			System.out.println(s.getJmbag() + " " + s.getFinalGrade());
		}
		
		System.out.println();
		
		for(StudentRecord s : vratiSortiranuListuOdlikasa(records)) {
			double points = s.getPointsExam1() + s.getPointsExam2() + s.getPointsLab();
			System.out.println(s.getJmbag() + " " + points + " " + s.getFinalGrade());
		}
		
		System.out.println();
		
		for(String s : vratiPopisNepolozenih(records)) {
			System.out.println(s);
		}
		
		System.out.println();
		
		Map<Integer, List<StudentRecord>> map = razvrstajStudentePoOcjenama(records);
		for(Map.Entry<Integer, List<StudentRecord>> entry : map.entrySet()) {
			for(StudentRecord s : entry.getValue()) {
				System.out.println(s.getJmbag() + " " + s.getFinalGrade());
			}
		}
		
		System.out.println();
		
		Map<Integer, Integer> map2 = vratiBrojStudenataPoOcjenama(records);
		for(Map.Entry<Integer, Integer> entry : map2.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		
		System.out.println();
		
		Map<Boolean, List<StudentRecord>> map3 = razvrstajProlazPad(records);
		for(Map.Entry<Boolean, List<StudentRecord>> entry : map3.entrySet()) {
			for(StudentRecord s : entry.getValue()) {
				System.out.println(entry.getKey() + " " + s.getJmbag() + " " + s.getFinalGrade());
			}
		}
	}
	
	/**
	 * This method is used to convert the read lines to StudentRecords.
	 * 
	 * @param lines
	 * @return
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		
		for(String line : lines) {
			String[] args = line.split("\\s+");
			
			StudentRecord record;
			try {
				record = new StudentRecord(args[0], args[1], args[2], Double.parseDouble(args[3]), 
						Double.parseDouble(args[4]), Double.parseDouble(args[5]), Integer.parseInt(args[6]));
			} catch(Exception e) {
				throw e;
			}
			
			records.add(record);
		}
		
		return records;
	}
	
	/**
	 * Takes a list of StudentRecords and returns the number of students that have earned more than
	 * 25 points in total.
	 * 
	 * @param records
	 * @return
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
				.filter(s -> s.getPointsExam1() + s.getPointsExam2() + s.getPointsLab() > 25)
				.count();
		return broj;
	}
	
	/**
	 * Takes a list of StudentRecords and returns the number of students that have earned the grade 5.
	 * 
	 * @param records
	 * @return
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj5 = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.count();
		
		return broj5;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a list consisting only of excellent students.
	 * 
	 * @param records
	 * @return
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.toList());
		return odlikasi;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a list consisting only of excellent students sorted
	 * by total score.
	 * 
	 * @param records
	 * @return
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.sorted(new Comparator<StudentRecord>() {
					 @Override
					public int compare(StudentRecord o1, StudentRecord o2) {
						 double points1 = o1.getPointsExam1() + o1.getPointsExam2() + o1.getPointsLab();
						 double points2 = o2.getPointsExam1() + o2.getPointsExam2() + o2.getPointsLab();
						if(points1 > points2) {
							return -1;
						}
						if(points1 < points2) {
							return 1;
						}
						return 0;
					}
				})
				.collect(Collectors.toList());
		return odlikasiSortirano;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a list of all students that have earned the grade 1.
	 * 
	 * @param records
	 * @return
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(s -> s.getFinalGrade() == 1)
				.map(s -> s.getJmbag())
				.sorted(new Comparator<String>() {
					public int compare(String o1, String o2) {
						if(o1.compareTo(o2) < 0) {
							return -1;
						}
						if(o1.compareTo(o2) > 0) {
							return 1;
						}
						return 0;
					};
				})
				.collect(Collectors.toList());
		
		return nepolozeniJMBAGovi;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a map that maps a certain grade to a list of students
	 * that have all earned that grade.
	 * 
	 * @param records
	 * @return
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
			.collect(Collectors.groupingBy(new Function<StudentRecord, Integer>() {
				@Override
				public Integer apply(StudentRecord t) {
					return t.getFinalGrade();
				}
			}));

		return mapaPoOcjenama;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a map that maps a certain grade to the number of students
	 * that have all earned that grade.
	 * 
	 * @param records
	 * @return
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(new Function<StudentRecord, Integer>() {
					@Override
					public Integer apply(StudentRecord t) {
						return t.getFinalGrade();
					};
				}, new Function<StudentRecord, Integer>() {
					@Override
					public Integer apply(StudentRecord t) {
						return 1;
					}
				}, new BinaryOperator<Integer>() {
					@Override
					public Integer apply(Integer t, Integer u) {
						return t + u;
					}
				}));
		
		return mapaPoOcjenama2;
	}
	
	/**
	 * Takes a list of StudentRecords and returns a map that maps a list of students that have passed the
	 * course to the value <code>true</code> and a list of students that haven't to the value <code>false</code>.
	 * 
	 * @param records
	 * @return
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
		
		return prolazNeprolaz;
	}
}
