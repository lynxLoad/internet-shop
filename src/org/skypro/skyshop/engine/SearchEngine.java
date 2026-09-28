package org.skypro.skyshop.engine;

import org.skypro.skyshop.Searchable;
import org.skypro.skyshop.exception.BestResultNotFound;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SearchEngine {
    private final List<Searchable> searchList;

    public SearchEngine(int length) {
        this.searchList = new LinkedList<>();
    }

    public Map<String, Searchable> search(String query) {
        Map<String, Searchable> result = new TreeMap<>();

        for (Searchable searchable : searchList) {
            if (searchable == null) {
                continue;
            }

            String searchTerm = searchable.getSearchTerm();

            if (searchTerm.toLowerCase().contains(query.toLowerCase())) {
                result.put(searchable.getName(), searchable);
            }
        }
        return result;
    }

    private int countMatchesInString(String string, String substring) {
        int count = 0;
        int index = 0;
        int substringIndex = string.indexOf(substring, index);

        while (substringIndex != -1) {
            count++;
            index = substringIndex + substring.length();
            substringIndex = string.indexOf(substring, index);
        }

        return count;
    }

    public Searchable searchMostSuitable(String query) throws BestResultNotFound {
        if (query == null || query.isEmpty()) {
            throw new BestResultNotFound("Не удалось найти товар по запросу: " + query);
        }

        Searchable result = null;
        int maxMatches = 0;

        for (Searchable searchable : searchList) {
            if (searchable == null) {
                continue;
            }

            String searchTerm = searchable.getSearchTerm();
            int matches = countMatchesInString(searchTerm.toLowerCase(), query.toLowerCase());

            if (matches > maxMatches) {
                maxMatches = matches;
                result = searchable;
            }
        }

        if (result == null) {
            throw new BestResultNotFound("Не удалось найти товар по запросу: " + query);
        }

        return result;
    }

    public void add(Searchable searchable) {
        searchList.add(searchable);
    }
}
