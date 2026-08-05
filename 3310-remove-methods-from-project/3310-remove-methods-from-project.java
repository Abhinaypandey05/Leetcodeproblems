class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] graph = new ArrayList[n];
        List<Integer>[] rev = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            rev[i] = new ArrayList<>();
        }

        for (int[] e : invocations) {
            graph[e[0]].add(e[1]);
            rev[e[1]].add(e[0]);
        }

        boolean[] suspicious = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(k);
        suspicious[k] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph[u]) {
                if (!suspicious[v]) {
                    suspicious[v] = true;
                    q.offer(v);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) continue;
            for (int p : rev[i]) {
                if (!suspicious[p]) {
                    List<Integer> ans = new ArrayList<>();
                    for (int j = 0; j < n; j++) ans.add(j);
                    return ans;
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) ans.add(i);
        }
        return ans;
    }
}