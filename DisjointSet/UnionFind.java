public class UnionFind {

    private int[] dset;
    private int size;
    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        dset = new int[n];
        for (int i = 0; i < n; i++) {
            dset[i] = -1;
        }
        size = n;
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Not valid index!");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        if (dset[v1] < 0) {
            return -1 * dset[v1];
        } else {
            return -1 * dset[find(v1)];
        }
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return dset[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        if (sizeOf(v1) == sizeOf(v2)) {
            if (find(v1) != find(v2)) {
                dset[find(v1)] = find(v2);
                dset[find(v2)] -= 1;
            }
        } else if (sizeOf(v1) > sizeOf(v2)) {
            dset[find(v2)] = find(v1);
            dset[find(v1)] -= 1;
        } else {
            dset[find(v1)] = find(v2);
            dset[find(v2)] -= 1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        validate(vertex);
        int r = vertex;
        while (parent(r) >= 0) {
            r = parent(r);
        }
        if (dset[vertex] > 0) {
            dset[vertex] = r;
        }
        return r;
    }

}
