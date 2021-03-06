package algorithm;
/**
 * 快速排序
 *
 * @author lvcy
 * @since 2021/9/16
 */
public class QuickSort {
    public int[] quickSort(int[] nums) {
        sort(nums, 0, nums.length - 1);
        for (int num : nums) {
            System.out.println(num);
        }
        return nums;
    }

//    第一次排序，获取seed的index，在index的两边进行快排
    public void sort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int index = partition(nums, left, right);
        sort(nums, left, index - 1);
        sort(nums, index + 1, right);
    }

    public int partition(int[] nums, int left, int right) {
        // 取两个临时变量作为指针不断靠近
        int i = left, j = right + 1;
        int seed = nums[left];
        while (true) {
            // 用++i直接跳过第一个种子元素，秒啊
            while (nums[++i] < seed) {
                if (i == right) {
                    break;
                }
            }
            // j已经+1了相当于一个末端的哨兵，秒啊
            while (nums[--j] > seed) {
                if (j == left) {
                    break;
                }
            }
            // 指针位置不对直接退出
            if (i >= j) {
                break;
            }
            // 指针位置正确直接交换位置
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        // 最后用j而不是i是因为最后j的位置必然小于等于seed(比如：4,2,3,1,5,6,7 最后j会跑到index为3的位置，而i会跑到index为4的位置)
        int tmp = nums[left];
        nums[left] = nums[j];
        nums[j] = tmp;
        return j;
    }

    public static void main(String[] args) {
        new QuickSort().quickSort(new int[]{5, 5,5,2,1,5,7,8,834,2,53,1,6,7,9});
    }
}
