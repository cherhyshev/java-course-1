package ru.spbau.mit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.fail;
import static ru.spbau.mit.SecondPartTasks.*;

public class SecondPartTasksTest {
    private static Path firstFile = Paths.get("FindQuotesFirst");
    private static Path secondFile = Paths.get("FindQuotesSecond");

    @BeforeClass
    public static void begin() throws IOException {
        Files.createFile(firstFile);
        Files.createFile(secondFile);
    }

    @AfterClass
    public static void end() throws IOException {
        Files.delete(firstFile);
        Files.delete(secondFile);
    }


    @Test
    public void testFindQuotes() throws IOException {
        FileWriter firstOut = new FileWriter((firstFile.toFile()));
        firstOut.write("First line of stupid file\n");
        firstOut.write("I know some shit about vershinaBora!");
        firstOut.close();

        FileWriter secondOut = new FileWriter((secondFile.toFile()));
        secondOut.write("You know nothing, John Snow\n");
        secondOut.write("How about some love love with your aunt?");
        secondOut.close();

        List<String> resultLines = findQuotes(Arrays.asList(firstFile.toString(),
                secondFile.toString()), "now");

        Assert.assertEquals(Arrays.asList("I know some shit about vershinaBora!",
                "You know nothing, John Snow"), resultLines);

    }

    @Test
    public void testPiDividedBy4() {
        Assert.assertEquals(Math.PI / 4, piDividedBy4(), 1e-3);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> testAuthorsMap = new HashMap<>();

        testAuthorsMap.put("Лямбда-Поэт", Collections.emptyList());

        testAuthorsMap.put("Пушкин А.С.", Arrays.asList("Духовной жаждою томим,\n" +
                "В пустыне мрачной я влачился, —\n" + "И шестикрылый серафим\n" +
                "На перепутье мне явился.\n" + "Перстами легкими как сон\n" +
                "Моих зениц коснулся он.\n" + "Отверзлись вещие зеницы,\n" +
                "Как у испуганной орлицы.\n" + "Моих ушей коснулся он, —\n" +
                "И их наполнил шум и звон:\n" + "И внял я неба содроганье,\n" +
                "И горний ангелов полет,\n" + "И гад морских подводный ход,\n" +
                "И дольней лозы прозябанье.\n" + "И он к устам моим приник,\n" +
                "И вырвал грешный мой язык,\n" + "И празднословный и лукавый,\n" +
                "И жало мудрыя змеи\n" + "В уста замершие мои\n" +
                "Вложил десницею кровавой.\n" + "И он мне грудь рассек мечом,\n" +
                "И сердце трепетное вынул,\n" + "И угль, пылающий огнем,\n" +
                "Во грудь отверстую водвинул.\n" + "Как труп в пустыне я лежал,\n" +
                "И бога глас ко мне воззвал:\n" + "«Восстань, пророк, и виждь, и внемли,\n" +
                "Исполнись волею моей,\n" + "И, обходя моря и земли,\n" +
                "Глаголом жги сердца людей»."));

        testAuthorsMap.put("Лермонтов М.Ю.", Arrays.asList("С тех пор как вечный судия\n" +
                "Мне дал всеведенье пророка,\n" + "В очах людей читаю я\n" +
                "Страницы злобы и порока.\n" + "Провозглашать я стал любви\n" +
                "И правды чистые ученья:\n" + "В меня все ближние мои\n" +
                "Бросали бешено каменья.\n" + "Посыпал пеплом я главу,\n" +
                "Из городов бежал я нищий,\n" + "И вот в пустыне я живу,\n" +
                "Как птицы, даром божьей пищи;\n" + "Завет предвечного храня,\n" +
                "Мне тварь покорна там земная;\n" + "И звезды слушают меня,\n" +
                "Лучами радостно играя.\n" + "Когда же через шумный град\n" +
                "Я пробираюсь торопливо,\n" + "То старцы детям говорят\n" +
                "С улыбкою самолюбивой:\n" + "«Смотрите: вот пример для вас!\n" +
                "Он горд был, не ужился с нами:\n" + "Глупец, хотел уверить нас,\n" +
                "Что бог гласит его устами!\n" + "Смотрите ж, дети, на него:\n" +
                "Как он угрюм, и худ, и бледен!\n" + "Смотрите, как он наг и беден,\n" +
                "Как презирают все его!»"));

        Assert.assertEquals("Пушкин А.С.", findPrinter(testAuthorsMap));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> firstPurchase = new HashMap<>();
        firstPurchase.put("brain", 1);
        firstPurchase.put("heart", 1);

        Map<String, Integer> secondPurchase = new HashMap<>();
        secondPurchase.put("heart", 1);
        secondPurchase.put("fingers", 20);

        Map<String, Integer> mergedPurchase = new HashMap<>();
        mergedPurchase.put("brain", 1);
        mergedPurchase.put("heart", 2);
        mergedPurchase.put("fingers", 20);

        Map<String, Integer> result = calculateGlobalOrder(Arrays.asList(firstPurchase, secondPurchase));

        Assert.assertEquals(mergedPurchase, result);

    }
}
