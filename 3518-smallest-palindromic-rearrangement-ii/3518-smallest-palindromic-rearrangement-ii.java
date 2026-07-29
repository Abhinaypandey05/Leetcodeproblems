class Solution {
    static final int LIMIT = 1_000_000;
    double[] logFact;

    public String smallestPalindrome(String s, int k) {
        int n = s.length();

        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;

        char mid = 0;
        int halfLen = 0;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            if ((cnt[i] & 1) == 1)
                mid = (char) ('a' + i);
            half[i] = cnt[i] / 2;
            halfLen += half[i];
        }

        logFact = new double[halfLen + 1];
        for (int i = 1; i <= halfLen; i++)
            logFact[i] = logFact[i - 1] + Math.log(i);

        if (countWays(half, halfLen, k) < k)
            return "";

        StringBuilder left = new StringBuilder();

        int remain = halfLen;

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0) continue;

                half[c]--;

                int ways = countWays(half, remain - 1, k);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    remain--;
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);

        if (mid != 0)
            ans.append(mid);

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private int countWays(int[] half, int total, int limit) {

        double logWays = logFact[total];

        for (int x : half)
            logWays -= logFact[x];

        if (logWays > Math.log(limit) + 1)
            return limit;

        long res = 1;
        int remain = total;

        for (int x : half) {

            if (x == 0) continue;

            res *= comb(remain, x, limit);

            if (res >= limit)
                return limit;

            remain -= x;
        }

        return (int) res;
    }

    private long comb(int n, int r, int limit) {

        if (r > n) return 0;

        r = Math.min(r, n - r);

        long ans = 1;

        for (int i = 1; i <= r; i++) {
            ans = ans * (n - r + i) / i;

            if (ans >= limit)
                return limit;
        }

        return ans;
    }
}