// 690. Employee Importance
// https://leetcode.com/problems/employee-importance/description/

// Solution 1 => BFS

/**
 * Time Complexity: O(n) since we iterate over each employee
 * Space Complexity: O(n) since we used map for O(1) search. In q at worst case we can have (n-1) items
 */

class Solution {
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>();
        Queue<Employee> q = new LinkedList<>();

        // hashmap for optimised O(1) search
        for(Employee e: employees){
            map.put(e.id, e);
        }

        q.add(map.get(id));

        int importance = 0;

        // process each employee while adding its importance and add subordinates to q
        while(!q.isEmpty()){
            Employee e = q.poll();

            importance = importance + e.importance;

            List<Integer> subordinates = e.subordinates;

            for(int i = 0 ; i < subordinates.size() ; i++){
                q.add(map.get(subordinates.get(i)));
            }
        }

        return importance;
    }
}

// Solution 2 => DFS

/**
 * Time Complexity: O(n) since we iterate over each employee
 * Space Complexity: O(n) since we used map for O(1) search and stack would alos have at max n-1 calls
 */

class Solution {

    Map<Integer, Employee> map;
    int importance;

    public int getImportance(List<Employee> employees, int id) {

        importance = 0;

        map = new HashMap<>();

        // store employees in map for o(1) search
        for(Employee e: employees){
            map.put(e.id, e);
        }

        // process employee from given id
        dfs(map.get(id));

        return importance;
    }

    private void dfs(Employee e){

        // add importance of current employee
        importance = importance + e.importance;

        // process each subordinate with new dfs call
        for(int sub: e.subordinates){
            dfs(map.get(sub));
        }
    }

}