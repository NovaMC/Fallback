package xyz.novaserver.fallback.player;

import org.bukkit.block.Block;

public class FallbackPlayer {

    private int bowScore;
    private int bowMultiplier;
    private Block lastBlockHit;
    private boolean isTagged;

    public FallbackPlayer() {
        bowScore = 0;
        bowMultiplier = 1;
        lastBlockHit = null;
        isTagged = false;
    }

    public int getScore() {
        return bowScore;
    }

    public void setScore(int bowScore) {
        this.bowScore = bowScore;
    }

    public int getMultiplier() {
        return bowMultiplier;
    }

    public void setMultiplier(int bowMultiplier) {
        this.bowMultiplier = bowMultiplier;
    }

    public Block getLastBlock() {
        return lastBlockHit;
    }

    public void setLastBlock(Block lastBlockHit) {
        this.lastBlockHit = lastBlockHit;
    }

    public boolean isLastBlock (Block blockToCheck) {
        if (lastBlockHit == null || blockToCheck == null) {
            return false;
        }

        return blockToCheck.getX() == lastBlockHit.getX() && blockToCheck.getY() == lastBlockHit.getY()
                && blockToCheck.getZ() == lastBlockHit.getZ() && blockToCheck.getType() == lastBlockHit.getType();
    }

    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }
}
