package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryImplTest {

    private DictionaryImpl dict = new DictionaryImpl();

    /**
     * Добавляем 2 пары (ожидаем size 1 и 2), потом добавляем пару с дублирующим ключом,
     * добавляем null'ы и очищаем хранилище
     */
    @Test
    public void testPutSize() {
        dict.put("key1", "value1");
        assertEquals(1, dict.size());
        dict.put("key2", "value2");
        assertEquals(2, dict.size());
        dict.put("key2", "value2");
        assertEquals(2, dict.size());
        dict.put(null, null);
        assertEquals(2, dict.size());
        dict.clear();
        assertEquals(0, dict.size());
    }

    /**
     * Добавили - проверили contains, удалили - проверили contains
     */
    @Test
    public void testPutContains() {
        dict.put("key1", "value1");
        assertTrue(dict.contains("key1"));
        dict.remove("key1");
        assertFalse(dict.contains("key1"));
    }

    /**
     * Добавили пару - проверили корректность взятия value,
     * удалили - возвращает null, get от null тоже вернет null
     */
    @Test
    public void testPutGet() {
        dict.put("key1", "value1");
        assertEquals("value1", dict.get("key1"));
        dict.put("key1", "value2");
        assertEquals("value2", dict.get("key1"));
        dict.remove("key1");
        assertEquals(null, dict.get("key1"));
        assertEquals(null, dict.get(null));
    }

    /**
     * Тест на рехэш (напоминаю, что у нас bucketSize == 11, при рехэше расширяется в 3 раза)
     */
    @Test
    public void testRehash() {
        for (int i = 1; i <= 50; i++) {
            System.out.println(dict.getBucketsSummary());
            String newKey = "key" + i;
            String newValue = "value" + i;
            System.out.println(newKey + " " + dict.get(newKey));
            dict.put(newKey, newValue);
        }
        for (int i = 1; i <= 50; i++) {
            System.out.println(dict.getBucketsSummary());
            String newKey = "key" + i;
            System.out.println(newKey + " " + dict.get(newKey));
            dict.remove(newKey);
        }
    }

    @Test
    public void remove() {
    }

    @Test
    public void clear() {
    }
}