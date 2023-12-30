package drgmod.cards.rare.powers;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.DeepPocketsPower;
import drgmod.powers.DrunkFormPower;
import drgmod.powers.ShieldPower;
import drgmod.util.CardStats;

public class DrunkForm extends BaseCard {

    public static final int MAGIC = 1;

    public static final String ID = makeID(DrunkForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DrunkForm() {
        super(ID, info);
        setMagic(MAGIC);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void upgrade(){
        super.upgrade();
        if(this.upgraded){
            upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DrunkFormPower(p, magicNumber)));
    }
}
