package drgmod.cards.common.skills;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.GainMineralsAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.BurnPower;
import drgmod.util.CardStats;

public class ReinforcedPowerDrills extends BaseCard {
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;
    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;

    public static final String ID = makeID(ReinforcedPowerDrills.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ReinforcedPowerDrills() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setBlock(BLOCK, UPG_BLOCK);
        setCustomVar("mine", 10, 5);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainMineralsAction(p, customVar("mine")));
        addToBot(new GainBlockAction(p, block));
        for (AbstractMonster k : AbstractDungeon.getMonsters().monsters){
            addToBot(new ApplyPowerAction(k, p, new BurnPower(k, magicNumber)));
        }
    }
}
