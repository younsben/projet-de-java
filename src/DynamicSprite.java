import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    protected double speed = 5;
    private double timeBetweenFrame = 200;
    private boolean isWalking = true;
    private final int spriteSheetNumberOfColumn = 10;


    public void keyReleased(KeyEvent e) {

    }
    public DynamicSprite(Image image, double x, double y, double width, double height, boolean isWalking, double speed, int timeBetweenFrame, Direction direction) {
        super(x, y, image, width, height);
        this.isWalking = isWalking;
        this.speed = speed;
        this.timeBetweenFrame = timeBetweenFrame;
        this.direction = direction;

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction){
            case NORTH:moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case EAST:moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }
        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }
    private void move(){
        switch (direction) {
            case NORTH:
                this.y -= speed;
                break;

            case SOUTH:
                this.y += speed;
                break;
            case EAST:
                this.x += speed;
                break;
            case WEST:
                this.x -= speed;
                break;
        }
    }
    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    @Override
    public void draw(Graphics g){
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);
// numero d'image de droite à gauche puis de haut en bas
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height),
                null);
        }
}