package cn.cube.base.core.util;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description:DrawUtil
 * Author:zhanglida
 * Date:2021/4/15
 * Email:406504302@qq.com
 */
public class LotteryUtil {


    public static <T extends Lottery> T lottery(List<T> lotteryList) {
        if (CollectionUtils.isEmpty(lotteryList)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID);
        }
        Collections.sort(lotteryList, Comparator.comparing(Lottery::getWeight));

        Integer weight = 0;
        for (Lottery award : lotteryList) {
            weight += award.getWeight();
        }

        Integer random = NumberUtils.random(1, weight);
        Integer comparer = 0;
        T result = null;
        for (T award : lotteryList) {
            comparer += award.getWeight();
            if (random <= comparer) {
                result = award;
                break;
            }
        }

        return result;
    }


    public interface Lottery {
         Integer getWeight();

    }
}
