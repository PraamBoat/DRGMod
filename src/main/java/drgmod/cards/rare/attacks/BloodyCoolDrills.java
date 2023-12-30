package drgmod.cards.rare.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.actions.GainMineralsAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.FreezePower;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class BloodyCoolDrills extends BaseCard {
    private static final int DAMAGE = 2;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final double MULTIPLIER = 1.5;


    public static final String ID = makeID(BloodyCoolDrills.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BloodyCoolDrills() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        tags.add(CustomTags.PICKAXE);
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        AbstractPower freeze = m.getPower(FreezePower.POWER_ID);
        if (freeze != null)
            freeze.amount *= this.MULTIPLIER;
        super.calculateCardDamage(m);
        if (freeze != null)
            freeze.amount /= this.MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++){
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
}
