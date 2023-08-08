package algorithm;

import java.util.HashMap;

/**
 * @author chaoyi.lv
 * @date 2023/4/25
 */
public class ThreeColorBall {

    public void getRes(int[] arr) {
        arr = new QuickSort().quickSort(arr);
        sort123(arr);
        for (int i : arr) {
            System.out.print(i);
        }
    }

    // z
    public static void sort123(int[] nums) {
        int count = nums.length / 3;
        int p1 = 0, p2 = count, p3 = count * 2;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                swap(nums, i, p1);
                p1++;
            } else if (nums[i] == 2) {
                swap(nums, i, p2);
                p2++;
            } else {
                swap(nums, i, p3);
                p3++;
            }
        }

    }
//    在遍历数组时，根据当前元素的值，将其放在对应的指针位置，并根据需要将指针循环回到开头，以实现重复 1, 2, 3 的顺序。时间复杂度为 $O(n)$，空间复杂度为 $O(1)$。

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
//        new ThreeColorBall().getRes(new int[]{3,1,2,1,3,3,2,2,1,1,2,3});
        int[] arr = new int[]{3,1,2,1,3,3,2,2,1,1,2,3};

        System.out.println(2 ^ 3);
    }

}
