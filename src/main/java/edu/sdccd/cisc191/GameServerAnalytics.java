package edu.sdccd.cisc191;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameServerAnalytics {

    public static List<String> findTopNUsernamesByRating(Collection<PlayerAccount> players, int n) {
        return players.stream()
                .sorted(Comparator.comparingDouble(PlayerAccount::rating).reversed()) // Sort
                .limit(n) // Limit number of players by n
                .map(PlayerAccount::username) // Add via name
                .toList(); // Change to List
    }

    public static Map<String, Double> averageRatingByRegion(Collection<PlayerAccount> players) {
        return players.stream()
                .collect(Collectors.groupingBy(
                        PlayerAccount::region, // Group via region
                        Collectors.averagingInt(PlayerAccount::rating) // Evaluate each group
                )); // remember that a map stores a key and value: there can't be multiple identical keys
    }

    public static Set<String> findDuplicateUsernames(Collection<PlayerAccount> players) {
        // count usernames
        Map<String, Long> counts = players.stream()
                .collect(Collectors.groupingBy(
                        PlayerAccount::username, // Group via username
                        Collectors.counting() // Replace list with its size
                ));

        // filter duplicates
        return counts.entrySet().stream()  // Map.Entry<String, Long>
                .filter(entry -> entry.getValue() > 1) // check which has more than one
                .map(entry -> entry.getKey()) // throw away values
                .collect(Collectors.toSet());
    }

    public static Map<String, List<String>> groupUsernamesByTier(Collection<PlayerAccount> players) {
        return players.stream()
                .sorted(Comparator.comparing(PlayerAccount::username)) // sort alphabetically
                .collect(Collectors.groupingBy(
                        GameServerAnalytics::tierFor, // groups of tiers
                        Collectors.mapping(PlayerAccount::username, Collectors.toList()) // accumulate into names
                ));
    }

    public static Map<String, List<String>> buildRecentMatchSummariesByPlayer(Collection<MatchRecord> matches) {
        // TODO: use a Map + collection logic or a stream-based approach
        return matches.stream()
                .flatMap(match -> Stream.of( // Stream<Map.Entry<String, String>>
                        Map.entry(match.playerOne().username(), match.summary()),
                        Map.entry(match.playerTwo().username(), match.summary())
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    public static <T> T pickHigherRated(T first, T second, Comparator<T> comparator) {
        return comparator.compare(first, second) >= 0 // If greater, give first
            ? first
            : second;
    }

    public static String tierFor(PlayerAccount player) {
        if (player.rating() < 1000) return "Bronze";
        if (player.rating() < 1400) return "Silver";
        return "Gold";
    }
}
