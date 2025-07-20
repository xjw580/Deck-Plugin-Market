package club.xiaojiawei.strategyplugintemplate

import club.xiaojiawei.hsscriptbase.config.log
import club.xiaojiawei.hsscriptbase.enums.RunModeEnum
import club.xiaojiawei.hsscriptcardsdk.bean.Card
import club.xiaojiawei.hsscriptcardsdk.bean.area.DeckArea
import club.xiaojiawei.hsscriptcardsdk.bean.area.HandArea
import club.xiaojiawei.hsscriptcardsdk.bean.area.PlayArea
import club.xiaojiawei.hsscriptcardsdk.bean.isValid
import club.xiaojiawei.hsscriptcardsdk.data.CARD_INFO_TRIE
import club.xiaojiawei.hsscriptcardsdk.data.CARD_WEIGHT_TRIE
import club.xiaojiawei.hsscriptcardsdk.enums.CardRaceEnum
import club.xiaojiawei.hsscriptcardsdk.enums.CardTypeEnum
import club.xiaojiawei.hsscriptcardsdk.status.WAR
import club.xiaojiawei.hsscriptstrategysdk.DeckStrategy

/**
 * @author 肖嘉威
 * @date 2024/9/29 16:58
 */
class TemplateStrategyDeck : DeckStrategy() {

    override fun name(): String {
//        套牌策略名
        return "偶数萨"
    }

    override fun getRunMode(): Array<RunModeEnum> {
//        策略允许运行的模式
        return arrayOf(RunModeEnum.WILD)
    }

    override fun deckCode(): String {
        return "套牌代码"
    }

    override fun id(): String {
        return "套牌唯一id"
    }


    override fun executeChangeCard(cards: HashSet<Card>) {
        //        TODO("执行换牌策略")
        val toList = cards.toList()
        for (card in toList) {
            if (card.cost > 2) {
//                不要哪张牌就直接移除
                cards.remove(card)
            }
        }
    }

    /**
     * 深度复制卡牌集合
     */
    fun deepCloneCards(sourceCards: List<Card>): MutableList<Card> {
        val copyCards = mutableListOf<Card>()
        sourceCards.forEach {
            copyCards.add(it.clone())
        }
        return copyCards
    }

    /**
     * 我的回合开始时将会自动调用此方法
     */
    override fun executeOutCard() {
//        TODO("执行出牌策略")


//        需要投降时将needSurrender设为true
//        needSurrender = true
//        获取全局war
        val war = WAR
        //        我方玩家
        val me = war.me
        if (!me.isValid()) return
//        敌方玩家
        val rival = war.rival
        if (!rival.isValid()) return
//            获取战场信息

//            获取我方所有手牌
        val handCards = me.handArea.cards
//            获取我方所有场上的卡牌
        val playCards = me.playArea.cards
//            获取我方英雄
        val hero = me.playArea.hero
//            获取我方武器
        val weapon = me.playArea.weapon
//            获取我方技能
        val power = me.playArea.power
//            获取我方所有牌库中的卡牌
        val deckCards = me.deckArea.cards
//            我方当前可用水晶数
        val usableResource = me.usableResource

//            cardId是游戏写死的，每张牌的cardId都是唯一不变的，如同身份证号码，
        val heroCardId = hero?.cardId
//            entityId在每局游戏中是唯一的
        val heroEntityId = hero?.entityId

        if (heroCardId == "ICC_833") {
            log.info { "该卡牌为 冰霜女巫吉安娜" }
        }

//        获取用户设置的卡牌权重
        val cardWeightTrie = CARD_WEIGHT_TRIE
//        冰霜女巫吉安娜的权重
        val weight = cardWeightTrie["ICC_833"]

//        获取用户设置的卡牌行为
        val cardInfoTrie = CARD_INFO_TRIE
//        冰霜女巫吉安娜的卡牌行为
        val cardInfo = cardInfoTrie["ICC_833"]


        /**
         * 获取卡牌的各项属性，如攻击力，费用，种族，是否为嘲讽。详情参考[BaseCard]
         * 卡牌属性的便捷封装，参考[Card]
         */

        /**
         * 卡牌存在于各个区域，如手牌区，牌库区，详情见[club.xiaojiawei.bean.area.Area]的子类
         */

//            执行操作

        /*
        注意：
        1.从war中获取到的数据都是实时更新的，
        2. 当我从手牌中打出一张随从牌时，handCards会自动删除对应的卡牌，playCards则会增加对应的卡牌（如果没被反制）
        3. 建议将集合中卡牌复制到新集合中，例：playCards.toMutableList() 或 playCards.toList()
        4. 集合中Card的属性也会实时变化，如果不想变化，可以深度拷贝集合，@see deepCloneCards(List<Card>)
        */
        val copyPlayCards = playCards.toMutableList()
//            攻击

        for (playCard in copyPlayCards) {
            if (playCard.canAttack()) {
//                    判断我方随从攻击力是否大于敌方英雄血量
                if (playCard.atc >= rival.playArea.hero!!.blood()) {
//                    我方随从攻击敌方英雄
                    playCard.action.attackHero()
                } else {
                    if (rival.playArea.cards.isNotEmpty()) {
                        val rivalPlayCard = rival.playArea.cards[0]
//                            如果对方随从拥有嘲讽
                        if (rivalPlayCard.isTaunt) {
//                                我方随从攻击敌方随从
                            playCard.action.attack(rivalPlayCard)
                            if (rivalPlayCard.isDead()){
                                log.info { "该随从已经死亡" }
                            }
                        }
                    }

                }
            }
        }

        val copyHandCards = handCards.toMutableList()
//            出牌
        for (handCard in copyHandCards) {
//                判断卡牌所在区域，当然这里的卡牌都在手牌区，仅作演示
            if (handCard.area is HandArea) {
                log.info { "该牌位于手牌区" }
            } else if (handCard.area is PlayArea) {
                log.info { "该牌位于战场区" }
            } else if (handCard.area is DeckArea) {
                log.info { "该牌位于牌库区" }
            }

            when (handCard.cardType) {
                CardTypeEnum.SPELL -> log.info { "该牌为法术" }
                CardTypeEnum.MINION -> log.info { "该牌为随从" }
                CardTypeEnum.HERO -> log.info { "该牌为英雄" }
                CardTypeEnum.HERO_POWER -> log.info { "该牌为英雄技能" }
                else -> log.info { "" }
            }
            if (handCard.cardRace === CardRaceEnum.DEMON) {
                log.info { "该牌是恶魔" }
            }

//                费用够
            if (handCard.cost <= me.usableResource) {
//                    直接打出
//                    handCard.action.power()
//                    打出到我方场上指定下标处
//                    handCard.action.power(1)
//                    打出到我方场上指定卡牌处
//                    handCard.action.power(card)
//                    如果有比较复杂的卡牌
//                    如果是阿莱克丝塔萨（生命值变15的那个）
                if (handCard.cardId == "EX1_561") {
//                        步骤：打出，指向敌方英雄（即战吼目标为敌方英雄）
                    handCard.action
                        .power()
                        ?.pointTo(rival.playArea.hero!!)
                }
            }
        }
    }

    override fun executeDiscoverChooseCard(vararg cards: Card): Int {
//        TODO("执行选择发现牌策略")
//        返回选择卡牌的下标，这里选择的第一张牌
        return 0
    }

}