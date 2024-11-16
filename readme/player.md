Player.java contains :

    Animation: A 2D array of BufferedImages representing the player's animations.
    animationtick: An integer representing the current animation tick.
    animationIndex: An integer representing the current index of the animation.
    animationSpeed: An integer representing the speed of the animation.
    player_action: An integer representing the current player action.
    left, up, right, down, jump, death, attacked: Boolean flags representing the player's current state (e.g., whether the player is moving left or right, jumping, attacking, etc.).
    deathAnimationFinished: A boolean flag representing whether the death animation has finished playing.
    move, attack, prepare_block, block, release_block: Boolean flags representing whether the player is currently moving, attacking, preparing to block, blocking, or releasing a block.
    playerSpeed: A float representing the player's movement speed.
    playerMove: A float representing the player's movement.
    xDrawOffset, yDrawOffset: Floats representing the offset of the player's position on the screen.
    airSpeed: A float representing the player's speed when in the air.
    gravity: A float representing the gravitational force affecting the player.
    jumpSpeed: A float representing the initial speed of the player's jump.
    fallSpeed: A float representing the speed at which the player falls.
    inAir: A boolean flag representing whether the player is currently in the air.
    hudStatusBar: A BufferedImage representing the player's HUD status bar.
    statusBarWidth, statusBarHeight, statusBarX, statusBarY, healthBarWidth, healthBarHeight, healthBarXStart, healthBarYStart: Integers representing the dimensions and positions of various elements on the HUD.
    maxHealth: An integer representing the player's maximum health.
    currentHealth: An integer representing the player's current health.
    healthWidth: An integer representing the width of the player's health bar.
    attackRange: A Rectangle2D.Float representing the player's attack range.
    attackChecked: A boolean flag representing whether the player's attack has been checked.
    flipX, flipW: Integers representing the flip values for the player's texture direction.
    LevelData: A 2D array of integers representing the level data.
    playing: A reference to the Playing game state.

Methods:

    player(): Constructor for the player class.
    initAttackRange(): Initializes the player's attack range.
    update(): Updates the player's state.
    checkAttack(): Checks whether the player's attack has hit an enemy.
    updateAttackRange(): Updates the player's attack range.
    updateHealthBar(): Updates the player's health bar.
    changeHealth(int value): Changes the player's health by a given value.
    LoadlevelData(int[][] LevelData): Loads the level data.
    setAnimation(): Sets the player's animation.
    resetAnimation(): Resets the player's animation.
    updateAnimation(): Updates the player's animation.