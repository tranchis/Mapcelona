class CoordinatesController < ApplicationController
  # GET /coordinates
  # GET /coordinates.xml
  def index
    @coordinates = Coordinate.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @coordinates }
    end
  end

  # GET /coordinates/1
  # GET /coordinates/1.xml
  def show
    @coordinate = Coordinate.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @coordinate }
    end
  end

  # GET /coordinates/new
  # GET /coordinates/new.xml
  def new
    @coordinate = Coordinate.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @coordinate }
    end
  end

  # GET /coordinates/1/edit
  def edit
    @coordinate = Coordinate.find(params[:id])
  end

  # POST /coordinates
  # POST /coordinates.xml
  def create
    @coordinate = Coordinate.new(params[:coordinate])

    respond_to do |format|
      if @coordinate.save
        flash[:notice] = 'Coordinate was successfully created.'
        format.html { redirect_to(@coordinate) }
        format.xml  { render :xml => @coordinate, :status => :created, :location => @coordinate }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @coordinate.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /coordinates/1
  # PUT /coordinates/1.xml
  def update
    @coordinate = Coordinate.find(params[:id])

    respond_to do |format|
      if @coordinate.update_attributes(params[:coordinate])
        flash[:notice] = 'Coordinate was successfully updated.'
        format.html { redirect_to(@coordinate) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @coordinate.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /coordinates/1
  # DELETE /coordinates/1.xml
  def destroy
    @coordinate = Coordinate.find(params[:id])
    @coordinate.destroy

    respond_to do |format|
      format.html { redirect_to(coordinates_url) }
      format.xml  { head :ok }
    end
  end
end
