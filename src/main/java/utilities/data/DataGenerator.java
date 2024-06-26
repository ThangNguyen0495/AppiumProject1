package utilities.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.utils.jsonFileUtility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static utilities.character_limit.CharacterLimit.*;

public class DataGenerator {
    public String generateString(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public String generateNumber(int length) {
        return RandomStringUtils.random(length, false, true);
    }

    public long generateLongNumber(long max) {
        Random rand = new Random();
        return rand.nextLong(max);
    }

    public int generatNumberInBound(int start, int end) {
        Random rand = new Random();
        int random_integer = rand.nextInt(end - start) + start;
        return random_integer;
    }

    /**
     * Returns a list of all the countries in the CountryCodes.json file as Strings.
     * @return a List of all the countries in the CountryCodes.json file
     */
    public List<String> getCountryList() {
        JsonNode data = jsonFileUtility.readJsonFile("CountryCodes.json");
        Iterator<String> it = data.fieldNames();
        List<String> countries = new ArrayList<>();
        while (it.hasNext()) {
            countries.add(it.next());
        }
        return countries;
    }

    /**
     * @return a random country
     */
    public String randomCountry() {
        List<String> countries = getCountryList();
        return countries.get(new Random().nextInt(0, countries.size()));
    }

    /**
     * Returns the phone code for a given country name as a String
     * @param country the name of the country to get the code for
     * @return the phone code for the given country, or null if it is not found
     */
    public String getPhoneCode(String country) {
        JsonNode data = jsonFileUtility.readJsonFile("CountryCodes.json").findValue(country).findValue("phoneCode");
        return data.asText();
    }

    /**
     * Returns the country code for a given country name as a String
     * @param country the name of the country to get the code for
     * @return the country code for the given country, or null if it is not found
     */
    public String getCountryCode(String country) {
        JsonNode data = jsonFileUtility.readJsonFile("CountryCodes.json").findValue(country).findValue("countryCode");
        return data.asText();
    }

    public static class UniqueRng implements Iterator<Integer> {
        private List<Integer> numbers = new ArrayList<>();

        public UniqueRng(int n) {
            for (int i = 1; i <= n; i++) {
                numbers.add(i);
            }

            Collections.shuffle(numbers);
        }

        @Override
        public boolean hasNext() {
            return !numbers.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return numbers.remove(0);
        }
    }

    public List<Integer> randomListNumberWithNoDuplicate(int maximum) {
        UniqueRng rng = new UniqueRng(maximum);
        List<Integer> list = new ArrayList<>();
        while (rng.hasNext()) {
            list.add(rng.next());
        }
        return list;
    }

    public List<Integer> randomListNumberCanDuplicate(int maximum) {
        List<Integer> listNumber = new ArrayList<>();
        for (int i = 0; i < maximum; i++) {
            listNumber.add(generatNumberInBound(1, maximum));
        }
        return listNumber;
    }

    /**
     * Generates a random number with the specified number of digits using the current epoch time as a seed.
     * @param numberOfDigits the number of digits in the random number to be generated
     * @return the randomly generated number as a String
     */
    public String randomNumberGeneratedFromEpochTime(int numberOfDigits) {
        long time = System.currentTimeMillis();
        System.out.println("Current Epoch time is: " + time);
        Matcher m = Pattern.compile("\\d{%d}$".formatted(numberOfDigits)).matcher(String.valueOf(time));
        String randomNumber = null;
        if (m.find()) {
            randomNumber = m.group();
        }
        System.out.println("Random number generated is: " + randomNumber);
        return randomNumber;
    }


    public String generateDateTime(String dateFormat, int... plusDate) {
        int plusDay = plusDate.length == 0 ? 0 : plusDate[0];
        return DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.now().plusDays(plusDay));
    }

    /**
     * generate Variation value
     */
    private List<String> generateListString(String defaultLanguage, int index, int size) {
        return IntStream.range(0, size).mapToObj(i -> "%s_var%s_%s".formatted(defaultLanguage, index, i + 1)).toList();
    }

    public List<Integer> getNumOfValuesOnEachGroup(int numberOfVariations, int numberOfGroups) {
        if (numberOfGroups == 1) return List.of(numberOfVariations);
        int factor = IntStream.range(2, numberOfVariations).filter(i -> numberOfVariations % i == 0).findFirst().orElse(1);
        return List.of(factor, numberOfVariations / factor);
    }
    /**
     * generate variation maps (variation name : get variation value)
     */
    public Map<String, List<String>> randomVariationMap(String defaultLanguage) {
        // generate number of variation groups
        int numberOfGroups = nextInt(MAX_VARIATION_QUANTITY) + 1;

        // init number of variation values
        int numberOfVariations = 1;//nextInt((numberOfGroups == 1) ? MAX_VARIATION_QUANTITY_FOR_EACH_VARIATION : MAX_VARIATION_QUANTITY_FOR_ALL_VARIATIONS) + 1;

        // get number of value of each group variation
        List<Integer> numberOfVariationValue = getNumOfValuesOnEachGroup(numberOfVariations, numberOfGroups);

        // generate random data for variation map
        return new TreeMap<>(IntStream.range(0, numberOfVariationValue.size())
                .boxed()
                .collect(Collectors.toMap(valueIndex -> "%s_var%s".formatted(defaultLanguage, valueIndex + 1),
                        valueIndex -> generateListString(defaultLanguage, valueIndex + 1, numberOfVariationValue.get(valueIndex)),
                        (a, b) -> b)));
    }

    /**
     * <p> get get variation value after mixed variation</p>
     * <p> example: var1 = {a, b, c} and var2 = {d}</p>
     * <p> with above variations, we have 3 variation value {a|d, b|d, c|d}</p>
     */
    public List<String> mixVariationValue(List<String> variationValueList1, List<String> variationValueList2) {
        List<String> mixedVariationValueList = new ArrayList<>();
        variationValueList1.forEach(var1 -> variationValueList2.stream().map(var2 -> "%s|%s".formatted(var1, var2)).forEach(mixedVariationValueList::add));
        return mixedVariationValueList;
    }

    public String getVariationName(Map<String, List<String>> variationMap, String language) {
        // get variation name
        List<String> varName = new ArrayList<>(variationMap.keySet());
        return IntStream.range(1, varName.size()).mapToObj(i -> "|%s_%s".formatted(language, varName.get(i))).collect(Collectors.joining("", "%s_%s".formatted(language, varName.get(0)), ""));
    }

    public List<String> getVariationList(Map<String, List<String>> variationMap, String language) {
        List<List<String>> varValue = new ArrayList<>(variationMap.values());
        List<String> variationList = new ArrayList<>();
        for (String var : varValue.get(0)) {
            variationList.add("%s_%s".formatted(language, var));
        }
        if (varValue.size() > 1) {
            for (int valueIndex = 1; valueIndex < varValue.size(); valueIndex++) {
                variationList = new DataGenerator()
                        .mixVariationValue(variationList, varValue.get(valueIndex));
            }
        }
        return variationList;
    }

    public List<String> getVariationList(Map<String, List<String>> variationMap) {
        List<List<String>> varValue = new ArrayList<>(variationMap.values());
        List<String> variationList = new ArrayList<>(varValue.get(0));
        if (varValue.size() > 1) {
            for (int valueIndex = 1; valueIndex < varValue.size(); valueIndex++) {
                variationList = new DataGenerator()
                        .mixVariationValue(variationList, varValue.get(valueIndex));
            }
        }
        return variationList;
    }

    /**
     * Generates a Vietnamese phone number based on Epoch time
     * @return a {@code String} representing the randomly generated phone number
     */
    public String randomVNPhone() {
        String phone = randomNumberGeneratedFromEpochTime(10);
        String nonZeroDigit = String.valueOf(generatNumberInBound(1, 10));
        if (phone.matches("^0[1-9]\\d+")) {
            return phone;
        }
        if (phone.matches("^(0|[1-9])0\\d+")) {
            return "0" + nonZeroDigit + phone.substring(2);
        }
        if (phone.matches("^[1-9][1-9]\\d+")) {
            return "0" + phone.substring(1);
        }
        return phone;
    }

    /**
     * Generates a foreign phone number based on Epoch time
     * @return a {@code String} representing the randomly generated phone number
     */
    public String randomForeignPhone() {
        String phone = randomNumberGeneratedFromEpochTime(10);
        String nonZeroDigit = String.valueOf(generatNumberInBound(1, 10));
        if (phone.matches("^[1-9]\\d+")) {
            return phone;
        }
        if (phone.matches("^0\\d+")) {
            return nonZeroDigit + phone.substring(1);
        }
        return phone;
    }

    /**
     * Generates a random phone number based on the specified country
     * @param country a {@code String} representing the name of the country
     * @return a {@code String} representing the randomly generated phone number
     */
    public String randomPhoneByCountry(String country) {
        return country.contentEquals("Vietnam") ? randomVNPhone() : randomForeignPhone();
    }

    public static <T> T getRandomListElement(List<T> list) {
        return list.get(new Random().nextInt(0, list.size()));
    }

    public String getCurrentDate(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @SneakyThrows
    public String getFilePath(String fileName) {
        File root = new File(System.getProperty("user.dir"));
        List<Path> paths = Files.walk(Paths.get(root.toString())).toList();
        Optional<Path> filePath = paths.stream()
                .filter(path -> !Files.isDirectory(path))
                .filter(path -> path.toString().contains("resources")
                                && path.getFileName().toString().equals(fileName))
                .findFirst();
        return filePath.map(Path::toString).orElse("");
    }

    @SneakyThrows
    public String getFolderPath(String folderName) {
        File root = new File(System.getProperty("user.dir"));
        List<Path> paths = Files.walk(Paths.get(root.toString())).toList();
        Optional<Path> folderPath = paths.stream()
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().equals(folderName))
                .findFirst();
        return folderPath.map(Path::toString).orElse("");
    }

    public List<String> getAllFileNamesInFolder(String folderName) {
        File root = new File(getFolderPath(folderName));
        return Arrays.stream(Objects.requireNonNull(root.listFiles())).filter(File::isFile).map(File::getName).toList();
    }


    public static String getFirstString(String... strings) {
        return Optional.ofNullable(strings).filter(stringArr -> stringArr.length > 0).map(stringArr -> stringArr[0]).orElse("");
    }

    public static String getStringByRegex(String inputString, String regex) {
        return Pattern.compile(regex).matcher(inputString)
                .results()
                .map(matchResult -> matchResult.group(1)).findFirst().orElse(null);
    }

    public static List<String> getListStringByRegex(String inputString, String regex) {
        return Pattern.compile(regex).matcher(inputString)
                .results()
                .map(matchResult -> matchResult.group(1))
                .toList();
    }

    public String generatePreviousTerm(String format) {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(getPreviousTerm(date));
    }

    public static Date getPreviousTerm(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        } else {
            calendar.roll(Calendar.MONTH, false);
        }
        calendar.set(Calendar.DATE, 01);
        return calendar.getTime();
    }
}
