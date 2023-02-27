import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author chaoyi.lv
 * @date 2023/1/16
 */
public class Solution {

    public String minWindow(String s, String t) {
        String min = "";
        if (s.length() < t.length()) {
            return min;
        }
        Map<Character, Integer> sub = getWordMap(t);

        int slow = 0;
        int fast = 0;
        Map<Character, Integer> map = getWordMap(s.substring(slow, fast + 1));
        while (true) {
            if (fast >= s.length() && !isSubStr(map, sub)) {
                break;
            }
            if (isSubStr(map, sub)) {
                min = min.length() == 0 ? s.substring(slow, fast + 1) : fast - slow < min.length() ? s.substring(slow, fast + 1) : min;
                while (fast < s.length() && sub.get(s.charAt(fast)) == null) {
                    fast ++;
                }
                slow ++;
                while (slow <= fast && sub.get(s.charAt(slow)) == null) {
                    slow ++;
                }
            } else {
                while (++ fast < s.length()) {
                    if (sub.get(s.charAt(fast)) == null) {
                        continue;
                    }
                    map.merge(s.charAt(fast), 1, Integer::sum);
                    if (isSubStr(map, sub)) {
                        min = min.length() == 0 ? s.substring(slow, fast + 1) : fast - slow < min.length() ? s.substring(slow, fast + 1) : min;
                        break;
                    }
                }
            }

        }
        return min;
    }

    public Map<Character, Integer> getWordMap(String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            Integer count = map.get(c);
            if (count == null) {
                map.put(c, 1);
            } else {
                map.put(c, count + 1);
            }
        }
        return map;
    }

    public boolean isSubStr(Map<Character, Integer> map, Map<Character, Integer> sub) {
        for (Map.Entry<Character, Integer> entry : sub.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            if (map.get(key) == null || map.get(key) < value) {
                return false;
            }
        }
        return true;
    }

    public List<String> letterCombinations(String digits) {
        int[][] arr = new int[10][4];
        int a = 'a';
        for (int i = 2; i <= 9; i ++) {
            arr[i][0] = a ++;
            arr[i][1] = a ++;
            arr[i][2] = a ++;
            if (i == 9) {
                arr[i][3] = a ++;
            }
        }
        List<String> queue = new ArrayList<>();
        if (digits.length() == 0) {
            return queue;
        }
        queue.add("");
        for (int i = 0; i < digits.length(); i ++) {
            List<String> temp = new ArrayList<>();
            int digit = Integer.parseInt(digits.substring(i, i + 1));
            for (int j = 0; j <= 3; j ++) {
                if (arr[digit][j] == 0) {
                    continue;
                }
                char c = (char) arr[digit][j];
                for (String s : queue) {
                    temp.add(s + c);
                }
            }
            queue = temp;
        }
        return queue;
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length - 1;
        int row;
        int col;
        if (matrix.length % 2 == 0) {
            row = matrix.length / 2;
            col = row;
        } else {
            row = (matrix.length + 1) / 2;
            col = row - 1;
        }

        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j ++) {
                int tmp = matrix[n - i][j];
                matrix[n - i][j] = matrix[n - i][n - j];
                matrix[n - i][n - j] = matrix[i][n - j];
                matrix[i][n - j] = matrix[i][j];
                matrix[i][j] = tmp;
            }
        }
    }


    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String count = getCount(str);
            List<String> list = map.get(str);
            if (list == null) {
                list = new ArrayList<>();
                list.add(str);
                map.put(count, list);
            } else {
                list.add(str);
            }
        }
        List<List<String>> res = new ArrayList<>(map.values());
        return res;
    }

    private String getCount(String str) {
        int[] arr = new int[26];
        for (char c : str.toCharArray()) {
            arr[c - 'a'] ++;
        }
        return Arrays.toString(arr);
    }


    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int max = 0;
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i ++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
            max = Math.max(dp[i], max);
        }
        return dp[nums.length - 1];
    }

    public boolean canJump(int[] nums) {
        boolean[] arrival = new boolean[nums.length];
        for (int i = 0; i < nums.length; i ++) {
            for (int j = 1; j <= nums[i]; j ++) {
                if (i + j < nums.length) {
                    arrival[i + j] = true;
                }
                if (i + j == nums.length - 1) {
                    return true;
                }
            }
        }
        return arrival[nums.length - 1];
    }


    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        Stack<int[]> stack = new Stack<>();
        for (int[] interval : intervals) {
            list.add(interval);
        }
        list.sort((x, y) -> x[0] - y[0]);
        for (int[] interval : list) {
            if (stack.isEmpty()) {
                stack.push(interval);
            } else {
                if (stack.peek()[1] >= interval[0]) {
                    int[] last = stack.pop();
                    stack.push(new int[]{last[0], Math.max(interval[1], last[1])});
                } else {
                    stack.push(interval);
                }
            }
        }
        int[][] res = new int[stack.size()][2];
        for (int i = 0; i < stack.size(); i ++) {
            res[i] = stack.pop();
        }
        return res;
    }


     static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        List<TreeNode> queue = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<TreeNode> tmp = new ArrayList<>();
            res.add(queue.stream().map(x -> x.val).collect(Collectors.toList()));

            for (TreeNode node : queue) {
                if (node.left != null) {
                    tmp.add(node.left);
                }
                if (node.right != null) {
                    tmp.add(node.right);
                }
            }
            queue = tmp;
        }
        return res;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] adjacenty = new int[numCourses];
        for (int[] pre : prerequisites) {
            adjacenty[pre[0]] ++;
            List<Integer> pres = map.get(pre[1]);
            if (pres == null) {
                pres = new ArrayList<>();
                pres.add(pre[0]);
                map.put(pre[1], pres);
            } else {
                pres.add(pre[0]);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i ++) {
            int degree = adjacenty[i];
            if (degree == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int i = queue.poll();
            if (map.get(i) == null) {
                continue;
            }
            for (int next : map.get(i)) {
                adjacenty[next] --;
                if (adjacenty[next] == 0) {
                    queue.add(next);
                }
            }
        }
        for (int i = 0; i < numCourses; i ++) {
            if (adjacenty[i] != 0) {
                return false;
            }
        }
        return true;
    }

    static class Trie {

        static class Node {
            char c;
            Set<Node> next;

            Node(char c) {this.c = c;}
        }
        Map<String, Boolean> exist;
        Node root;
        public Trie() {
            exist = new HashMap<>();
            root = new Node('*');
        }

        public void insert(String word) {
            exist.put(word, true);
            Node cur = root;
            for (int i = 0; i < word.length(); i ++) {
                char c = word.charAt(i);
                Set<Node> next = cur.next;
                if (next == null) {
                    next = new HashSet<>();
                    Node nw = new Node(word.charAt(i));
                    next.add(nw);
                    cur.next = next;
                    cur = nw;
                } else {
                    Node nw = null;
                    for (Node node : next) {
                        if (node.c == c) {
                            nw = node;
                            break;
                        }
                    }
                    if (nw == null) {
                        nw = new Node(c);
                        next.add(nw);
                        cur = nw;
                    } else {
                        cur = nw;
                    }
                }
            }
        }

        public boolean search(String word) {
            return exist.get(word) != null;
        }

        public boolean startsWith(String prefix) {
            Node cur = root;
            for (int i = 0; i < prefix.length(); i ++) {
                char c = prefix.charAt(i);
                Set<Node> next = cur.next;
                if (next == null) {
                    return false;
                }
                boolean contains = false;
                for (Node node : next) {
                    if (node.c == c) {
                        cur = node;
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Your Trie object will be instantiated and called as such:
     * Trie obj = new Trie();
     * obj.insert(word);
     * boolean param_2 = obj.search(word);
     * boolean param_3 = obj.startsWith(prefix);
     */

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] path = new boolean[m][n];
        int i = 0, j = 0;
        while (i >= 0 && j >= 0 && i < m && j < n) {
            path[i][j] = true;
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                if (i > 0 && !path[i - 1][j]) {
                    i --;
                } else {
                    break;
                }
            } else {
                if (j < n - 1 && !path[i][j + 1]) {
                    j ++;
                } else if (i < m - 1) {
                    i ++;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    public void moveZeroes(int[] nums) {
        for (int i = 0; i < nums.length; i ++) {
            if (i == 0) {
                for (int j = i; j < nums.length - 1; j ++) {
                    exch(nums, j, j + 1);
                }
            }
        }
    }

    private void exch(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (slow == fast) {
                return slow;
            }
        }
    }

    public String decodeString(String s) {
        String res = "";
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i ++) {
            char c = s.charAt(i);
            if (c == ']') {
                String times = "";
                String repeat = "";
                String str = "";
                while (!deque.isEmpty() && deque.peekLast() != '[') {
                    repeat = deque.pollLast() + repeat;
                }
                deque.pollLast();
                while (!deque.isEmpty() && deque.peekLast() - '0' < 10) {
                    times += deque.pollLast();
                }
                System.out.println(times);
                int count = Integer.valueOf(times);
                for (int j = 0; j < count; j ++) {
                    str += repeat;
                }
                for (int j = 0; j < str.length(); j ++) {
                    deque.add(str.charAt(j));
                }
            } else {
                deque.add(c);
            }
        }
        while (!deque.isEmpty()) {
            res += deque.poll();
        }
        return res;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] arr1 = new int[26];
        int[] arr2 = new int[26];
        int len1 = s.length();
        int len2 = p.length();

        for (int i = 0; i < len2; i ++) {
            arr2[p.charAt(i) - 'a'] ++;
        }
        int l = 0, r = 0;
        while (r < len2) {
            int head = s.charAt(r ++) - 'a';
            arr1[head] ++;
            while (arr1[head] > arr2[head]) {
                int tail = s.charAt(l ++) - 'a';
                arr1[tail] --;
            }
            if (isAnagram(arr1, arr2)) {
                res.add(l);
            }
        }
        return res;
    }

    private boolean isAnagram(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length;i ++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public int findTargetSumWays(int[] nums, int target) {
        int[][] dp = new int[nums.length][2001];
        dp[0][1000 - nums[0]] = 1;
        dp[0][nums[0] + 1000] = 1;
        for (int i = 1; i < nums.length; i ++) {
            for (int j = 0; j < 2001; j ++) {
                if (j + nums[i] < 2001) {
                    dp[i][j] += dp[i - 1][j + nums[i]];
                }
                if (j >= nums[i]) {
                    dp[i][j] += dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[nums.length - 1][target];
    }

    public static void main(String[] args) {
        new Solution().findTargetSumWays(new int[]{0,0,0,0,0,0,0,0,1}, 3);
    }

}
