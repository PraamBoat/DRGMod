package drgmod.cards.rare.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import drgmod.actions.IntoxicationRemoveAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

import java.util.ArrayList;

public class AbyssBar extends BaseCard {
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public static final String ID = makeID(AbyssBar.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public AbyssBar() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++){
//            CardGroup temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
            while (!(c.hasTag(CustomTags.ALCOHOL))){
                c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
                c.exhaust = true;
                c.freeToPlayOnce = true;
            }
            addToBot(new MakeTempCardInHandAction(c, true));
////            ArrayList<AbstractCard> cards = new ArrayList<>();
//            for (AbstractCard c : cards){
//                if (c.hasTag(CustomTags.PICKAXE)){
//                    temp.group.add(c);
//                }
//            }
//            int size = temp.size();
//            int k =
        }
    }
}
