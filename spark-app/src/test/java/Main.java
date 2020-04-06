import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 

public class Main {
	
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskG solver = new TaskG();
        solver.solve(1, in, out);
        out.close();
    }
 
    static class TaskG {
        final long infinity = (long) 1e15;
 
        public void solve(int testNumber, FastScanner in, PrintWriter out) {
            int numWords = in.nextInt();
            char[][] words = new char[numWords][];
            int[] wordCost = new int[numWords];
            for (int i = 0; i < numWords; i++) {
                words[i] = in.next().toCharArray();
                System.out.println("in.next().toCharArray():"+words[i]);
                wordCost[i] = in.nextInt();
            }
            System. exit(0);
//        final boolean stress = true;
            final boolean stress = false;
            if (stress) {
                words = new char[1][1000];
                Arrays.fill(words[0], 'a');
                wordCost = new int[1];
            }
            AhoCorasick ac = new AhoCorasick(words, wordCost);
            int n = ac.numNodes;
            int[][] f = ac.combinedNext;
            int[] score = ac.nodeScore;
 
            long[][] d = new long[n][1 << ac.ALPHA];
            for (long[] arr : d) {
                Arrays.fill(arr, -infinity);
            }
            d[ac.root][0] = 0;
            int[] remap = new int[n];
            for (int i = 0; i < n; i++) {
                remap[i] = i;
            }
            long[] remapScore = new long[n];
            char[] s = in.next().toCharArray();
            if (stress) {
                s = "??????????????".toCharArray();
            }
            long[][] nd = new long[n][1 << ac.ALPHA];
            for (char ch : s) {
                if (ch != '?') {
                    for (int i = 0; i < n; i++) {
                        int j = f[remap[i]][ch - 'a'];
                        remap[i] = j;
                        remapScore[i] += score[j];
                    }
                    continue;
                }
 
                applyRemap(d, nd, remap, remapScore);
                long[][] t = d;
                d = nd;
                nd = t;
                for (long[] arr : nd) {
                    Arrays.fill(arr, -infinity);
                }
                for (int i = 0; i < n; i++) {
                    for (int mask = 0; mask < 1 << ac.ALPHA; mask++) {
                        if (d[i][mask] < -infinity / 2) {
                            continue;
                        }
                        for (int c = 0; c < ac.ALPHA; c++) {
                            if (((mask >> c) & 1) != 0) {
                                continue;
                            }
                            int j = f[i][c];
                            int nmask = mask | (1 << c);
                            nd[j][nmask] = Math.max(nd[j][nmask], d[i][mask] + score[j]);
                        }
                    }
                }
                t = d;
                d = nd;
                nd = t;
            }
            applyRemap(d, nd, remap, remapScore);
            long[][] t = d;
            d = nd;
            nd = t;
 
            long ans = -infinity;
            for (int i = 0; i < n; i++) {
                for (int mask = 0; mask < 1 << ac.ALPHA; mask++) {
                    ans = Math.max(ans, d[i][mask]);
                }
            }
            out.println(ans);
        }
 
        private void applyRemap(long[][] d, long[][] nd, int[] remap, long[] remapScore) {
            int n = d.length;
            for (long[] arr : nd) {
                Arrays.fill(arr, -infinity);
            }
            for (int i = 0; i < n; i++) {
                for (int mask = 0; mask < d[i].length; mask++) {
                    nd[remap[i]][mask] = Math.max(nd[remap[i]][mask], d[i][mask] + remapScore[i]);
                }
            }
            for (int i = 0; i < n; i++) {
                remap[i] = i;
            }
            Arrays.fill(remapScore, 0);
        }
 
        class AhoCorasick {
            int[][] trieNext;
            int[] parent;
            int[] parentC;
            int[] nodeScore;
            int[] failureLink;
            int[][] failureNext;
            int[][] combinedNext;
            int numNodes;
            final int ALPHA = 14;
            final int root = 0;
 
            AhoCorasick(char[][] words, int[] wordScores) {
                final int initialCapacity = 10;
                trieNext = new int[initialCapacity][];
                parent = new int[initialCapacity];
                parentC = new int[initialCapacity];
                nodeScore = new int[initialCapacity];
                failureLink = new int[initialCapacity];
                failureNext = new int[initialCapacity][];
                newNode(); // root
                failureLink[root] = -1;
 
                for (int i = 0; i < words.length; i++) {
                    addWord(words[i], wordScores[i]);
                }
 
                buildFailureLinks();
                buildCombinedTransitions();
                adjustNodeScores();
            }
 
            private int newNode() {
                if (numNodes == trieNext.length) {
                    int k = 3 * numNodes / 2;
                    trieNext = Arrays.copyOf(trieNext, k);
                    parent = Arrays.copyOf(parent, k);
                    parentC = Arrays.copyOf(parentC, k);
                    nodeScore = Arrays.copyOf(nodeScore, k);
                    failureLink = Arrays.copyOf(failureLink, k);
                    failureNext = Arrays.copyOf(failureNext, k);
                }
                int r = numNodes++;
                trieNext[r] = new int[ALPHA];
                Arrays.fill(trieNext[r], -1);
                parent[r] = -1;
                parentC[r] = -1;
                nodeScore[r] = 0;
                failureLink[r] = root;
                failureNext[r] = new int[ALPHA];
                Arrays.fill(failureNext[r], root);
                return r;
            }
 
            private void addWord(char[] w, int score) {
                int cur = root;
                for (int i = 0; i < w.length; i++) {
                    int c = w[i] - 'a';
                    if (trieNext[cur][c] < 0) {
                        trieNext[cur][c] = newNode();
                    }
                    int x = trieNext[cur][c];
                    parent[x] = cur;
                    parentC[x] = c;
                    cur = x;
                }
                nodeScore[cur] += score;
            }
 
            private void buildFailureLinks() {
                int[] order = bfs();
                for (int v : order) {
                    if (v == root) {
                        continue;
                    }
                    int p = parent[v];
                    int pc = parentC[v];
 
                    failureLink[v] = failureNext[p][pc];
                    for (int c = 0; c < ALPHA; c++) {
                        int f = failureLink[v];
                        if (trieNext[f][c] >= 0) {
                            failureNext[v][c] = trieNext[f][c];
                        } else if (failureNext[f][c] >= 0) {
                            failureNext[v][c] = failureNext[f][c];
                        }
                    }
                }
            }
 
            private void buildCombinedTransitions() {
                combinedNext = new int[numNodes][ALPHA];
                for (int i = 0; i < numNodes; i++) {
                    for (int c = 0; c < ALPHA; c++) {
                        int j = trieNext[i][c];
                        if (j < 0) {
                            j = failureNext[i][c];
                        }
                        if (j < 0) {
                            throw new AssertionError();
                        }
                        combinedNext[i][c] = j;
                    }
                }
            }
 
            void adjustNodeScores() {
                int[] order = bfs();
                for (int v : order) {
                    if (v != root) {
                        nodeScore[v] += nodeScore[failureLink[v]];
                    }
                }
            }
 
            private int[] bfs() {
                int[] q = new int[numNodes];
                boolean[] was = new boolean[numNodes];
                int qt = 0;
                int qh = 1;
                q[0] = root;
                was[root] = true;
                while (qt < qh) {
                    int v = q[qt++];
                    for (int c = 0; c < ALPHA; c++) {
                        int u = trieNext[v][c];
                        if (u < 0) {
                            continue;
                        }
                        if (was[u]) {
                            continue;
                        }
                        q[qh++] = u;
                        was[u] = true;
                    }
                }
                if (qt != numNodes) {
                    throw new AssertionError();
                }
                return q;
            }
 
        }
 
    }
 
    static class FastScanner {
        private BufferedReader in;
        private StringTokenizer st;
 
        public FastScanner(InputStream stream) {
            in = new BufferedReader(new InputStreamReader(stream));
        }
 
        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(in.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}