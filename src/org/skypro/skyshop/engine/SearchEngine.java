package org.skypro.skyshop.engine;

import org.skypro.skyshop.Searchable;
import org.skypro.skyshop.exception.BestResultNotFound;

import java.util.*;

public class SearchEngine {
    private final Set<Searchable> searchList;

    public SearchEngine(int length) {
        this.searchList = new HashSet<>(length);
    }

    public Set<Searchable> search(String query) {
        Comparator<Searchable> comparator = (a, b) -> {
            int lengthCompare = Integer.compare(b.getNameLength(), a.getNameLength());
            if (lengthCompare != 0) {
                return lengthCompare;
            }
            return a.getName().compareTo(b.getName());
        };

        Set<Searchable> result = new TreeSet<>(comparator);

        for (Searchable searchable : searchList) {
            if (searchable == null) {
                continue;
            }

            String searchTerm = searchable.getSearchTerm();

            if (searchTerm.toLowerCase().contains(query.toLowerCase())) {
                result.add(searchable);
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
