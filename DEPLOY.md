# Deploy to Render

This guide will help you deploy the URL Shortener to Render platform.

## 🚀 Quick Deploy

### Option 1: Deploy with render.yaml (Recommended)

1. **Push your code to GitHub**
   ```bash
   git add .
   git commit -m "Add Render deployment config"
   git push origin main
   ```

2. **Connect to Render**
   - Go to [render.com](https://render.com)
   - Sign up/Login with your GitHub account
   - Click "New +" → "Blueprint"
   - Connect your GitHub repository
   - Render will automatically detect the `render.yaml` file

3. **Deploy**
   - Render will create both the web service and PostgreSQL database
   - The deployment will start automatically
   - Wait for the build to complete

### Option 2: Manual Deploy

1. **Create PostgreSQL Database**
   - Go to Render Dashboard
   - Click "New +" → "PostgreSQL"
   - Choose "Free" plan
   - Set name: `url-shortener-db`
   - Choose region (Oregon recommended for free tier)
   - Click "Create Database"

2. **Create Web Service**
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Choose "Docker" environment
   - Set name: `url-shortener`
   - Choose "Free" plan
   - Set build command: `echo "Building with Docker..."`
   - Set start command: `echo "Starting with Docker..."`
   - Add environment variables:
     ```
     SPRING_PROFILES_ACTIVE=production
     SERVER_PORT=8080
     SPRING_DATASOURCE_URL=<your-db-connection-string>
     SPRING_DATASOURCE_USERNAME=<your-db-username>
     SPRING_DATASOURCE_PASSWORD=<your-db-password>
     SPRING_JPA_HIBERNATE_DDL_AUTO=update
     SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
     ```

## 🔧 Environment Variables

| Variable | Value | Description |
|----------|-------|-------------|
| `SPRING_PROFILES_ACTIVE` | `production` | Production profile |
| `SERVER_PORT` | `8080` | Application port |
| `SPRING_DATASOURCE_URL` | Auto | Database connection string |
| `SPRING_DATASOURCE_USERNAME` | Auto | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Auto | Database password |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Auto-create tables |
| `SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT` | `org.hibernate.dialect.PostgreSQLDialect` | PostgreSQL dialect |

## 📊 Free Tier Limits

- **Web Service**: 750 hours/month
- **Database**: 90 days trial, then $7/month
- **Build Time**: 500 minutes/month
- **Bandwidth**: 100 GB/month

## 🌐 Access Your App

After deployment, your app will be available at:
```
https://your-app-name.onrender.com
```

## 📝 Notes

- **Free tier**: Service sleeps after 15 minutes of inactivity
- **Cold starts**: First request after sleep may take 30-60 seconds
- **Database**: PostgreSQL is automatically managed by Render
- **SSL**: HTTPS is automatically enabled
- **Custom domains**: Available on paid plans

## 🐛 Troubleshooting

### Build Fails
- Check that all dependencies are in `pom.xml`
- Ensure Dockerfile is in the root directory
- Verify Java 17 compatibility

### Database Connection Issues
- Wait for database to be fully provisioned
- Check environment variables are correctly set
- Verify database credentials in Render dashboard

### App Not Starting
- Check logs in Render dashboard
- Verify `SERVER_PORT` is set to 8080
- Ensure JAR file is being built correctly
