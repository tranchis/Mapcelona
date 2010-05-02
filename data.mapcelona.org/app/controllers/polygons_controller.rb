class PolygonsController < ApplicationController
  # GET /polygons
  # GET /polygons.xml
  def index
    @polygons = Polygon.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @polygons }
    end
  end

  # GET /polygons/1
  # GET /polygons/1.xml
  def show
    @polygon = Polygon.find(params[:id])

    respond_to do |format|
      #format.html # show.html.erb
      #format.xml  { render :xml => @polygon }
      format.rdf
    end
  end

  # GET /polygons/new
  # GET /polygons/new.xml
  def new
    @polygon = Polygon.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @polygon }
    end
  end

  # GET /polygons/1/edit
  def edit
    @polygon = Polygon.find(params[:id])
  end

  # POST /polygons
  # POST /polygons.xml
  def create
    @polygon = Polygon.new(params[:polygon])

    respond_to do |format|
      if @polygon.save
        flash[:notice] = 'Polygon was successfully created.'
        format.html { redirect_to(@polygon) }
        format.xml  { render :xml => @polygon, :status => :created, :location => @polygon }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @polygon.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /polygons/1
  # PUT /polygons/1.xml
  def update
    @polygon = Polygon.find(params[:id])

    respond_to do |format|
      if @polygon.update_attributes(params[:polygon])
        flash[:notice] = 'Polygon was successfully updated.'
        format.html { redirect_to(@polygon) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @polygon.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /polygons/1
  # DELETE /polygons/1.xml
  def destroy
    @polygon = Polygon.find(params[:id])
    @polygon.destroy

    respond_to do |format|
      format.html { redirect_to(polygons_url) }
      format.xml  { head :ok }
    end
  end
end
