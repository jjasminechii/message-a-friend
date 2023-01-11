package ckpt2;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChainingHashTableTests {

	private void incrementValueWithKey(Dictionary<String, Integer> list, String key) {
		Integer find = list.find(key);
		if (find == null)
			list.insert(key, 1);
		else
			list.insert(key, find + 1);
	}

	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_insertFind_manyElements_correctStructure() {
		ChainingHashTable<String, Integer> list = new ChainingHashTable<>(MoveToFrontList::new);

		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j++)
				incrementValueWithKey(list, str);
		}

		// Delete them all
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, (int) dc.value);
			totalCount += dc.value;
		}
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(list.size(), n);
		assertNotNull(list.find("00851"));
		assertEquals(4260, (int) list.find("00851"));
	}

//	@Test()
//	public void testFuzz() {
//		Hashtable<Integer, Integer> reference = new Hashtable();
//		ChainingHashTable<Integer, Integer> list = new ChainingHashTable<>(MoveToFrontList::new);
//		Random randy = new Random();
//
//		for (int i = 0; i < 100000; i++) {
//			int testCase = randy.nextInt(10);
//			if (testCase < 6) {
//				// 60% of the time, try to insert something.
//				int value = randy.nextInt();
//				char c = (char) (randy.nextInt(26) + 'a');
//				String p = String.valueOf(c);
//				reference.put(i, i);
//				list.insert(i, i);
////			} else if (!reference.isEmpty()) {
////				// the remaining 40% of the time, try to delete something.
////				assertEquals("removed element should be same",
////						reference.remove(), student.next());
////			}
//			}
//		}
//			// After applying the operation, we can check how things are doing.
//			// use Java's built-in methods to check state.
//			assertEquals(reference.size(), list.size());
////			if (reference.size() > 0) {
//
//				// If you're feeling aggressive, you can also check every single value.
//				Hashtable<Integer, Integer> referenceList = new Hashtable<>(reference);
//				for (int idx = 0; idx < reference.size(); idx++) {
//					assertEquals(referenceList.get(idx), list.find(idx));
//		}
//	}
}
