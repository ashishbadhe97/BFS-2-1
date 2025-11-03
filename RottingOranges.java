// 994. Rotting Oranges
// https://leetcode.com/problems/rotting-oranges/description/

/**
 * Time Complexity: O(m * n) since we iterate over entire matrix
 * Space Complexity: O(m * n) since in worst case we could store (m * n - 1) elements in queue
 */

// Solution 1 => BFS

class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int []> q = new LinkedList<>();

        int dirs[][] = new int[][] { {-1, 0}, {1,0}, {0,-1}, {0, 1}};

        int fresh = 0;

        int m = grid.length;
        int n = grid[0].length;

        int time = 0;
         
        // We first add all rotten oranges (i.e 2) in queue for BFS and count fresh oranges
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(grid[i][j] == 2){
                    q.add(new int[] {i , j});
                }

                if(grid[i][j] == 1){
                    fresh++;
                }
            }
        }

        // if no fresh oranges, then time taken would be 0
        if(fresh == 0){
            return time;
        }

        // process the rotten oranges in queue
        while(!q.isEmpty()){
            int size = q.size();

            time++;

            for(int i = 0 ; i < size ; i++){
                int[] curr = q.poll();

                // Check if there are any fresh oranges in up, down, left, right directions
                // if yes, make them rotten and keep track of remaining fresh oranges
                for(int[] dir: dirs){
                    int r = curr[0] + dir[0];
                    int c = curr[1] + dir[1];

                    // check if adjacent nodes are within bounds and if it is fresh,
                    // if yes, rot the orange ad add it to queue
                    if(r >= 0 && c >= 0 && r < m && c < n && grid[r][c] == 1){
                        grid[r][c] = 2;
                        q.add(new int[] {r, c});
                        fresh--;
                    }
                }

                if(fresh == 0){
                    return time;
                }

            }
        }

        return -1;
    }
}

// Solution 2 => DFS

/**
 * Time Complexity: O(m * n)
 * Space Complexity:  O(m * n) since in worst case we can have (m * n) calls i.e call for each cell
 */

class Solution {

    int m;
    int n;
    int dirs[][];

    public int orangesRotting(int[][] grid) {

        m = grid.length;
        n = grid[0].length;

        dirs = new int[][] { {-1, 0}, {1,0}, {0,-1}, {0, 1}};
        
        // Start dfs from 2 and update distance of 1 from each 2
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(grid[i][j] == 2){
                    dfs(i, j, grid, 3); // starting time from 3, since time can be 1,2 which is different from rotten and fresh oranges
                }   
            }
        }


        int max = 2;

        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(grid[i][j] == 1){ // if we still have fresh oranges, return
                    return -1;
                }

                max = Math.max(max, grid[i][j]);
            }
        }

        return max - 2;
    }

    private void dfs(int i, int j, int[][] grid, int time){

        // logic
        for(int[] dir: dirs){
            int r = i + dir[0];
            int c = j + dir[1];

            // Check if there are any fresh oranges in up, down, left, right directions
            // if yes, update time grid with time and move forward
            if(r >= 0 && c >= 0 && r < m && c < n && (grid[r][c] == 1 || grid[r][c] > time)){
                grid[r][c] = time;
                dfs(r, c, grid, time + 1);
            }
        }

    }
}